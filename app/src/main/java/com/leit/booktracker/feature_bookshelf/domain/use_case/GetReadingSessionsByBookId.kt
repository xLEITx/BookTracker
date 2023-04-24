package com.leit.booktracker.feature_bookshelf.domain.use_case

import com.leit.booktracker.feature_bookshelf.data.relations.BookWithReadingSessions
import com.leit.booktracker.feature_bookshelf.domain.repository.BookTrackerRepository

class GetReadingSessionsByBookId(
    private val repository: BookTrackerRepository
) {
    suspend operator fun invoke(bookId:Int): List<BookWithReadingSessions> {
        return repository.getBookWithReadingSessions(bookId)
    }
}