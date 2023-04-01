package com.leit.booktracker.feature_bookshelf.domain.use_case

import com.leit.booktracker.feature_bookshelf.domain.model.Book
import com.leit.booktracker.feature_bookshelf.domain.repository.BookTrackerRepository
import com.leit.booktracker.feature_bookshelf.domain.util.BookOrder
import com.leit.booktracker.feature_bookshelf.domain.util.BookStatus
import com.leit.booktracker.feature_bookshelf.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetBooks(
    private val repository:BookTrackerRepository
) {

    operator fun invoke(
        bookOrder: BookOrder = BookOrder.Status(OrderType.Ascending)
    ): Flow<List<Book>>{
        return repository.getBooks().map { books->
            when(bookOrder.orderType){
                is OrderType.Ascending ->{
                    when(bookOrder){
                        is BookOrder.Status -> books.sortedBy {
                            when(it.status){
                                BookStatus.IN_PROGRESS -> 0
                                BookStatus.IN_WISHLIST -> 1
                                BookStatus.ON_BOOKSHELF -> 2
                                else -> 3
                            }
                        }
                        is BookOrder.Title -> books.sortedBy { it.title.lowercase() }
                        is BookOrder.Author -> books.sortedBy { it.author.lowercase() }
                        is BookOrder.Type ->  books.sortedBy { it.type.lowercase() }
                    }


                }

                is OrderType.Descending ->{
                    when(bookOrder){
                        is BookOrder.Status -> books.sortedByDescending{
                            when(it.status){
                                BookStatus.IN_PROGRESS -> 0
                                BookStatus.IN_WISHLIST -> 1
                                BookStatus.ON_BOOKSHELF -> 2
                                else -> 3
                            }
                        }
                        is BookOrder.Title -> books.sortedByDescending { it.title.lowercase() }
                        is BookOrder.Author -> books.sortedByDescending { it.author.lowercase() }
                        is BookOrder.Type ->  books.sortedByDescending { it.type.lowercase() }
                    }
                }
            }
        }
    }

}