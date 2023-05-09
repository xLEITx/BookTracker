package com.leit.booktracker.feature_bookshelf.presentation.bookshelf

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leit.booktracker.feature_bookshelf.domain.model.Book
import com.leit.booktracker.feature_bookshelf.domain.use_case.BookShelfUseCases
import com.leit.booktracker.feature_bookshelf.domain.util.BookFilter
import com.leit.booktracker.feature_bookshelf.domain.util.BookOrder
import com.leit.booktracker.feature_bookshelf.domain.util.BookStatus
import com.leit.booktracker.feature_bookshelf.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookShelfViewModel @Inject constructor(
    private val bookShelfUseCases:BookShelfUseCases
): ViewModel() {

    private val _state = mutableStateOf(BookShelfState())
    val state: State<BookShelfState> = _state

    private var recentlyDeletedBook: Book? = null

    private var getBooksJob: Job? = null

    init {
        getBooks(BookOrder.Status(OrderType.Descending), state.value.bookFilter)
    }

    fun onEvent(event: BookShelfEvent){
        when(event){
            is BookShelfEvent.Order ->{
                if(state.value.bookOrder::class == event.bookOrder::class &&
                        state.value.bookOrder.orderType == event.bookOrder.orderType){
                    return
                }
                getBooks(event.bookOrder, state.value.bookFilter)
            }
            is BookShelfEvent.FilterChange ->{
                _state.value = state.value.copy(
                    bookFilter = event.bookFilter
                )
                getBooks(state.value.bookOrder, event.bookFilter)
            }
            is BookShelfEvent.DeleteBook ->{
                viewModelScope.launch{
                    bookShelfUseCases.deleteBook(event.book)
                    recentlyDeletedBook = event.book
                }
            }
            is BookShelfEvent.ChangeStatus ->{
                viewModelScope.launch {
                   bookShelfUseCases.insertBook(
                        event.book.copy(status = event.status)
                   )
                }
            }
            is BookShelfEvent.RestoreBook ->{
                viewModelScope.launch {
                    bookShelfUseCases.insertBook(recentlyDeletedBook?: return@launch)
                    recentlyDeletedBook = null
                }
            }
            is BookShelfEvent.ToggleOrderSection ->{
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getBooks(
        bookOrder: BookOrder,
        bookFilter: BookFilter
    ){
        getBooksJob?.cancel()

        getBooksJob = bookShelfUseCases.getBooks(bookOrder)
            .onEach {books ->
                _state.value = state.value.copy(
                    books = books,
                    bookOrder = bookOrder
                )
                filterBooks(bookFilter)
            }.launchIn(viewModelScope)
    }

    private fun filterBooks(bookFilter: BookFilter){
        if (!bookFilter.isInProgress){
            _state.value = state.value.copy(
                books = state.value.books.filterNot { book ->
                    book.status == BookStatus.IN_PROGRESS
                }
            )
        }
        if (!bookFilter.isOnBookshelf){
            _state.value = state.value.copy(
                books = state.value.books.filterNot { book ->
                    book.status == BookStatus.ON_BOOKSHELF
                }
            )
        }
        if (!bookFilter.isInWishlist){
            _state.value = state.value.copy(
                books = state.value.books.filterNot { book ->
                    book.status == BookStatus.IN_WISHLIST
                }
            )
        }
        if (!bookFilter.isFinished){
            _state.value = state.value.copy(
                books = state.value.books.filterNot { book ->
                    book.status == BookStatus.FINISHED
                }
            )
        }
    }

}