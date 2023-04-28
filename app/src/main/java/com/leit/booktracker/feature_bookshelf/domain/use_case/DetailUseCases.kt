package com.leit.booktracker.feature_bookshelf.domain.use_case

data class DetailUseCases(
    val getSingleBook: GetSingleBook,
    val insertReadingSession: InsertReadingSession,
    val getNotes: GetNotes,
    val calculatePagesToDate: CalculatePagesToDate,
    val getReadingSessionsByBookId: GetReadingSessionsByBookId,
    val getNotesByBookId: GetNotesByBookId
)