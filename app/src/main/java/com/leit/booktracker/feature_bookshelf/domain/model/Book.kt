package com.leit.booktracker.feature_bookshelf.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leit.booktracker.feature_bookshelf.domain.util.BookStatus
@Entity
data class Book(
    @PrimaryKey val bookId:Int,
    val title:String,
    val author:String,
    val type:String,
    val status:String,
    val pagesCount:Int,
    val coverPath:String? = null
)
