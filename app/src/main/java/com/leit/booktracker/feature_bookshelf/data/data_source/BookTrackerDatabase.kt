package com.leit.booktracker.feature_bookshelf.data.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.leit.booktracker.feature_bookshelf.domain.model.Book
import com.leit.booktracker.feature_bookshelf.domain.model.ReadingSession

@Database(entities = [
        Book::class,
        ReadingSession::class
    ],
    version = 1,
    exportSchema = false
)
abstract class BookTrackerDatabase: RoomDatabase() {

    abstract val bookTrackerDao:BookTrackerDao

    companion object{
        const val DATABASE_NAME = "books_db"
    }
/*    companion object{
        @Volatile
        private var INSTANCE:BookTrackerDatabase? = null

        fun getInstance(context:Context):BookTrackerDatabase{
            synchronized(this){
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    BookTrackerDatabase::class.java,
                    "book_tracker_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    } */
}