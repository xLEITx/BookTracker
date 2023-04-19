package com.leit.booktracker.feature_bookshelf.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ReadingSession(
    @PrimaryKey(autoGenerate = true) val id:Int? = null,
    val pages:Int,
    val timestamp:Long,
    val bookId:Int
)
