package com.leit.booktracker.feature_bookshelf.presentation.util

import com.leit.booktracker.feature_bookshelf.domain.model.Note
import com.leit.booktracker.feature_bookshelf.domain.util.NoteOrder

data class NotesListState(
    val notes: List<Note> = emptyList(),
    val order: NoteOrder = NoteOrder.Date
)