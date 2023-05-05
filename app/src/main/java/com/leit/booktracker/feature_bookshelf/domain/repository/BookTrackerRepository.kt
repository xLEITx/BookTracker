package com.leit.booktracker.feature_bookshelf.domain.repository

import com.leit.booktracker.feature_bookshelf.data.relations.BookWithReadingSessions
import com.leit.booktracker.feature_bookshelf.domain.model.Book
import com.leit.booktracker.feature_bookshelf.domain.model.Note
import com.leit.booktracker.feature_bookshelf.domain.model.ReadingSession
import kotlinx.coroutines.flow.Flow

interface BookTrackerRepository {

    fun getBooks():Flow<List<Book>>

    suspend fun getBookById(bookId:Int):Book?

    suspend fun insertBook(book: Book)

    suspend fun deleteBook(book: Book)

    suspend fun getBookWithReadingSessions(bookId:Int):List<BookWithReadingSessions>

    suspend fun insertReadingSession(readingSession: ReadingSession)

    fun getNotes():Flow<List<Note>>

    fun getNotesByBookId(bookId: Int):Flow<List<Note>>

    suspend fun getNoteById(id:Int):Note?


    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun deleteNoteById(id: Int)
}