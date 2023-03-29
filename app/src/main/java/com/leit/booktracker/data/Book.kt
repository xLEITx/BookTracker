package com.leit.booktracker.data

import androidx.annotation.DrawableRes

data class Book(
    val title:String,
    val author:String,
    @DrawableRes val cover:Int?,
    val status:BookStatus,
    val type:String
)

enum class BookStatus {
    IN_PROGRESS, IN_WISHLIST, ON_BOOKSHELF
}

