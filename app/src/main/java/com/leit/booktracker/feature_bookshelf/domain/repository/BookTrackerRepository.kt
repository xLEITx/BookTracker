package com.leit.booktracker.feature_bookshelf.domain.repository

import com.leit.booktracker.feature_bookshelf.data.relations.BookWithReadingSessions
import com.leit.booktracker.feature_bookshelf.domain.model.Book
import com.leit.booktracker.feature_bookshelf.domain.model.ReadingSession
import kotlinx.coroutines.flow.Flow

interface BookTrackerRepository {

    fun getBooks():Flow<List<Book>>

    suspend fun getBookById(bookId:Int):Book?

    suspend fun insertBook(book: Book)

    suspend fun deleteBook(book: Book)

    suspend fun getBookWithReadingSessions(bookId:Int):List<BookWithReadingSessions>

    suspend fun insertReadingSession(readingSession: ReadingSession)


}