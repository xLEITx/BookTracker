package com.leit.booktracker.feature_bookshelf.presentation.book_detail

import com.leit.booktracker.feature_bookshelf.domain.model.Book
import com.leit.booktracker.feature_bookshelf.domain.model.Note
import com.leit.booktracker.feature_bookshelf.domain.util.NoteOrder
import java.time.LocalDate

data class BookDetailState(
    val book: Book = Book(title = "", author = "", type = "", status = "", pagesCount = 0),
    val readPages: Int = 0,
    val initialReadPages: Int = 0,
    val chosenDate: LocalDate = LocalDate.now().plusDays(7),
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date
)