package com.leit.booktracker.feature_bookshelf.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Book(
    @PrimaryKey(autoGenerate = true) val bookId:Int? = null,
    val title:String,
    val author:String,
    val type:String,
    val status:String,
    val pagesCount:Int,
    val coverPath:String? = null
)

class InvalidBookException(message:String):Exception(message)