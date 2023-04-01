package com.leit.booktracker.feature_bookshelf.data.repository

import com.leit.booktracker.feature_bookshelf.data.data_source.BookTrackerDao
import com.leit.booktracker.feature_bookshelf.data.relations.BookWithReadingSessions
import com.leit.booktracker.feature_bookshelf.domain.model.Book
import com.leit.booktracker.feature_bookshelf.domain.model.ReadingSession
import com.leit.booktracker.feature_bookshelf.domain.repository.BookTrackerRepository
import kotlinx.coroutines.flow.Flow

class BookTrackerRepositoryImpl(
    private val dao: BookTrackerDao
):BookTrackerRepository {
    override fun getBooks(): Flow<List<Book>> {
        return dao.getBooks()
    }

    override suspend fun getBookById(bookId: Int): Book? {
        return dao.getBookById(bookId)
    }

    override suspend fun insertBook(book:Book) {
        dao.insertBook(book)
    }

    override suspend fun deleteBook(book: Book) {
        dao.deleteBook(book)
    }

    override suspend fun getBookWithReadingSessions(bookId: Int): List<BookWithReadingSessions> {
        return dao.getBookWithReadingSessions(bookId)
    }

    override suspend fun insertReadingSession(readingSession: ReadingSession) {
        dao.insertReadingSession(readingSession)
    }
}