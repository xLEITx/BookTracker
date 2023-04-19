package com.leit.booktracker.feature_bookshelf.presentation.util

import com.leit.booktracker.feature_bookshelf.domain.util.BookStatus

object StatusOptions {
    val options = listOf(
        BookStatus.IN_PROGRESS,
        BookStatus.IN_WISHLIST,
        BookStatus.ON_BOOKSHELF,
        BookStatus.FINISHED
    )
}