package com.leit.booktracker.feature_bookshelf.presentation.book_detail

import com.leit.booktracker.feature_bookshelf.domain.util.NoteOrder


sealed class BookDetailEvent {
    data class ChangeReadPages(val value: Int) : BookDetailEvent()
    data class ChangeNoteOrder(val value: NoteOrder):BookDetailEvent()
    object SaveReadingSession : BookDetailEvent()
}