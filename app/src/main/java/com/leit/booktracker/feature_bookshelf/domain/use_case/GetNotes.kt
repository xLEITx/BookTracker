package com.leit.booktracker.feature_bookshelf.domain.use_case

import com.leit.booktracker.feature_bookshelf.domain.model.Note
import com.leit.booktracker.feature_bookshelf.domain.repository.BookTrackerRepository
import com.leit.booktracker.feature_bookshelf.domain.util.NoteOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(
    private val repository: BookTrackerRepository
) {
    operator fun invoke(noteOrder: NoteOrder = NoteOrder.Title): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when(noteOrder){
                is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                is NoteOrder.Date -> notes.sortedBy { it.timestamp }
            }
        }
    }
}