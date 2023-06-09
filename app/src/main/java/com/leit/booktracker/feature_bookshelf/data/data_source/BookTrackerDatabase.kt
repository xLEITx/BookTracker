package com.leit.booktracker.feature_bookshelf.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.leit.booktracker.feature_bookshelf.domain.model.Book
import com.leit.booktracker.feature_bookshelf.domain.model.Note
import com.leit.booktracker.feature_bookshelf.domain.model.ReadingSession

@Database(entities = [
        Book::class,
        ReadingSession::class,
        Note::class
    ],
    version = 1,
    exportSchema = false
)
abstract class BookTrackerDatabase: RoomDatabase() {

    abstract val bookTrackerDao:BookTrackerDao

    companion object{
        const val DATABASE_NAME = "books_db"
    }
}