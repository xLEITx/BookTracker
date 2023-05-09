package com.leit.booktracker.feature_bookshelf.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.leit.booktracker.feature_bookshelf.data.relations.BookWithReadingSessions
import com.leit.booktracker.feature_bookshelf.domain.model.Book
import com.leit.booktracker.feature_bookshelf.domain.model.Note
import com.leit.booktracker.feature_bookshelf.domain.model.ReadingSession
import kotlinx.coroutines.flow.Flow

@Dao
interface BookTrackerDao {

    @Query("SELECT * FROM book")
    fun getBooks(): Flow<List<Book>>

    @Query("SELECT * FROM book WHERE status = :status")
    fun getBooks(status:String):Flow<List<Book>>

    @Query("SELECT * FROM book WHERE bookId = :bookId ")
    suspend fun getBookById(bookId:Int):Book?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)

    @Transaction
    @Query("SELECT * FROM book WHERE bookId = :bookId")
    suspend fun getBookWithReadingSessions(bookId:Int):List<BookWithReadingSessions>

    @Insert
    suspend fun insertReadingSession(readingSessions: ReadingSession)

    @Query("SELECT * FROM note")
    fun getNotes():Flow<List<Note>>

    @Query("SELECT * FROM note WHERE BookId = :bookId ")
    fun getNotesByBookId(bookId: Int):Flow<List<Note>>

    @Query("SELECT * FROM note WHERE Id = :id ")
    suspend fun getNoteById(id:Int):Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("DELETE FROM note WHERE Id = :id")
    suspend fun deleteNoteById(id: Int)
}