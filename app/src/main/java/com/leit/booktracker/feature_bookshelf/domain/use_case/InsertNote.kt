package com.leit.booktracker.feature_bookshelf.domain.use_case

import com.leit.booktracker.feature_bookshelf.domain.model.Note
import com.leit.booktracker.feature_bookshelf.domain.repository.BookTrackerRepository

class InsertNote(
    private val repository: BookTrackerRepository
) {
    suspend operator fun invoke(note:Note){
        repository.insertNote(note)
    }
}