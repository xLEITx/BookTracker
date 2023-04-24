package com.leit.booktracker.feature_bookshelf.presentation.book_detail

import java.time.LocalDate

sealed class BookDetailEvent {
    data class ChangeReadPages(val value: Int) : BookDetailEvent()
    data class CalculatePages(val chosenDate: LocalDate) : BookDetailEvent()
    object SaveReadingSession : BookDetailEvent()
}