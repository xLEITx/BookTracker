package com.leit.booktracker.feature_bookshelf.data.repository

import com.leit.booktracker.feature_bookshelf.data.data_source.BookTrackerDao
import com.leit.booktracker.feature_bookshelf.data.relations.BookWithReadingSessions
import com.leit.booktracker.feature_bookshelf.domain.model.Book
import com.leit.booktracker.feature_bookshelf.domain.model.Note
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

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
       return dao.getNoteById(id)
    }

    override fun getNotesByBookId(bookId: Int): Flow<List<Note>> {
        return dao.getNotesByBookId(bookId)
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }
}