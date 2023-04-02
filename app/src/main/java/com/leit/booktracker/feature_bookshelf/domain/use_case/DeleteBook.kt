package com.leit.booktracker.feature_bookshelf.domain.use_case

import com.leit.booktracker.feature_bookshelf.domain.model.Book
import com.leit.booktracker.feature_bookshelf.domain.repository.BookTrackerRepository

class DeleteBook(
    private val repository: BookTrackerRepository
) {

    suspend operator fun  invoke(book: Book){
        repository.deleteBook(book)
    }

}