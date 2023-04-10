package com.leit.booktracker.feature_bookshelf.domain.use_case

import com.leit.booktracker.feature_bookshelf.domain.model.Book
import com.leit.booktracker.feature_bookshelf.domain.model.InvalidBookException
import com.leit.booktracker.feature_bookshelf.domain.repository.BookTrackerRepository

class InsertBook(
    private val repository: BookTrackerRepository
) {
    @Throws(InvalidBookException::class)
    suspend operator fun invoke(book: Book){
        if (book.title.isBlank()){
            throw InvalidBookException("The title can`t be empty")
        }
        if(book.author.isBlank()){
            throw InvalidBookException("The author can`t be empty")
        }

        repository.insertBook(book)
    }
}