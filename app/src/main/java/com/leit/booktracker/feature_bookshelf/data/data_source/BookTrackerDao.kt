package com.leit.booktracker.feature_bookshelf.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.leit.booktracker.feature_bookshelf.data.relations.BookWithReadingSessions
import com.leit.booktracker.feature_bookshelf.domain.model.Book
import kotlinx.coroutines.flow.Flow

@Dao
interface BookTrackerDao {

    @Query("SELECT * FROM book")
    fun getBooks(): Flow<List<Book>>

    @Query("SELECT * FROM book WHERE bookId = :bookId ")
    suspend fun getBookById(bookId:Int):Book?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)

    @Transaction
    @Query("SELECT * FROM book WHERE bookId = :bookId")
    suspend fun getBookWithReadingSessions(bookId:Int):List<BookWithReadingSessions>

}