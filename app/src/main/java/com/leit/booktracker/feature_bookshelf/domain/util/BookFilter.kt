package com.leit.booktracker.feature_bookshelf.domain.util

data class BookFilter(
    val isInProgress:Boolean,
    val isInWishlist:Boolean,
    val isOnBookshelf:Boolean,
    val isFinished:Boolean
)
