package com.leit.booktracker.feature_bookshelf.domain.use_case

import com.leit.booktracker.feature_bookshelf.domain.model.InvalidNoteException
import com.leit.booktracker.feature_bookshelf.domain.model.Note
import com.leit.booktracker.feature_bookshelf.domain.repository.BookTrackerRepository
import kotlin.jvm.Throws

class InsertNote(
    private val repository: BookTrackerRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note:Note){

        //TODO: Find out a way to use string resources.
        if (note.title.isBlank()){
            throw InvalidNoteException("Title can`t be empty")
        }
        if (note.content.isEmpty()){
            throw InvalidNoteException("Content can`t be empty")
        }

        repository.insertNote(note)
    }
}