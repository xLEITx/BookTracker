package com.leit.booktracker.feature_bookshelf.domain.use_case

import com.leit.booktracker.feature_bookshelf.domain.model.Note
import com.leit.booktracker.feature_bookshelf.domain.repository.BookTrackerRepository

class DeleteNote(
    private val repository: BookTrackerRepository
) {
    suspend operator fun invoke(note:Note){
        repository.deleteNote(note)
    }
}