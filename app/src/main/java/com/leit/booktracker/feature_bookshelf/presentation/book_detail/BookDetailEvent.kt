package com.leit.booktracker.feature_bookshelf.presentation.book_detail

import java.time.LocalDateTime

sealed class BookDetailEvent {
    data class ChangeReadPages(val value: Int) : BookDetailEvent()
    data class CalculatePages(val chosenDate: LocalDateTime) : BookDetailEvent()
    object SaveReadingSession : BookDetailEvent()
}