package com.leit.booktracker.feature_bookshelf.domain.use_case

data class AddEditNoteUseCases(
    val getSingleNote: GetSingleNote,
    val insertNote: InsertNote,
    val deleteNote: DeleteNote,
    val deleteNoteById: DeleteNoteById
)