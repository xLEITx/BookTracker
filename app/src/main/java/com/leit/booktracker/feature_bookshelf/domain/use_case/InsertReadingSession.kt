package com.leit.booktracker.feature_bookshelf.domain.use_case

import com.leit.booktracker.feature_bookshelf.domain.model.ReadingSession
import com.leit.booktracker.feature_bookshelf.domain.repository.BookTrackerRepository

class InsertReadingSession(
    private val repository: BookTrackerRepository
) {
    suspend operator fun invoke(readingSession: ReadingSession){
        repository.insertReadingSession(readingSession)
    }
}