package com.leit.booktracker.feature_bookshelf.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val Id:Int? = null,
    val title:String,
    val content:String,
    val timestamp:Long,
    val bookId:Int
)