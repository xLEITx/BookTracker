package com.leit.booktracker.feature_bookshelf.presentation.bookshelf

import com.leit.booktracker.feature_bookshelf.domain.model.Book
import com.leit.booktracker.feature_bookshelf.domain.util.BookOrder
import com.leit.booktracker.feature_bookshelf.domain.util.BookStatus

sealed class BookShelfEvent{
    data class Order(val bookOrder: BookOrder): BookShelfEvent()
    data class DeleteBook(val book:Book):BookShelfEvent()
    data class ChangeStatus(val book: Book,val status:BookStatus):BookShelfEvent()
    object RestoreBook: BookShelfEvent()
    object ToggleOrderSection: BookShelfEvent()

}
