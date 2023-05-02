package com.leit.booktracker.feature_bookshelf.presentation.book_detail


sealed class BookDetailEvent {
    data class ChangeReadPages(val value: Int) : BookDetailEvent()
    object SaveReadingSession : BookDetailEvent()
}