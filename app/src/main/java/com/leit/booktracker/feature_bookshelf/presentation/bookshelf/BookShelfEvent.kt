package com.leit.booktracker.feature_bookshelf.presentation.bookshelf

import com.leit.booktracker.feature_bookshelf.domain.model.Book
import com.leit.booktracker.feature_bookshelf.domain.util.BookOrder

sealed class BookShelfEvent{
    data class Order(val bookOrder: BookOrder): BookShelfEvent()
    data class DeleteBook(val book:Book):BookShelfEvent()
    //Don`t forget to use BookStatus object to inject `status` param
    data class ChangeStatus(val book: Book,val status:String):BookShelfEvent()
    object RestoreBook: BookShelfEvent()
    object ToggleOrderSection: BookShelfEvent()

}
