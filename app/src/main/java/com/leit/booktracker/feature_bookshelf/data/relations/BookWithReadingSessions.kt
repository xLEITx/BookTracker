package com.leit.booktracker.feature_bookshelf.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.leit.booktracker.feature_bookshelf.domain.model.Book
import com.leit.booktracker.feature_bookshelf.domain.model.ReadingSession


data class BookWithReadingSessions(
    @Embedded val book: Book,
    @Relation(
        parentColumn = "bookId",
        entityColumn = "bookId"
    )
    val readingSessions: List<ReadingSession>
)