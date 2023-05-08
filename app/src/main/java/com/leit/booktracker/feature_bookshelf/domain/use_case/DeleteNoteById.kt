package com.leit.booktracker.feature_bookshelf.domain.use_case

import com.leit.booktracker.feature_bookshelf.domain.repository.BookTrackerRepository

class DeleteNoteById(
    private val repository: BookTrackerRepository
) {
    suspend operator fun invoke(id:Int){
        repository.deleteNoteById(id)
    }
}