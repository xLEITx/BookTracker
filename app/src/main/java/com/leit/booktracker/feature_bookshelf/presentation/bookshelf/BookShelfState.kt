package com.leit.booktracker.feature_bookshelf.presentation.bookshelf

import com.leit.booktracker.feature_bookshelf.domain.model.Book
import com.leit.booktracker.feature_bookshelf.domain.util.BookFilter
import com.leit.booktracker.feature_bookshelf.domain.util.BookOrder
import com.leit.booktracker.feature_bookshelf.domain.util.OrderType

data class BookShelfState(
    val books:List<Book> = emptyList(),
    val bookOrder:BookOrder = BookOrder.Status(OrderType.Descending),
    val isOrderSectionVisible:Boolean = false,
    val bookFilter: BookFilter  = BookFilter(
        isFinished = true,
        isInProgress = true,
        isInWishlist = true,
        isOnBookshelf = true
    )
)
