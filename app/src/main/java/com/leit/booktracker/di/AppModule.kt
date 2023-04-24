package com.leit.booktracker.di

import android.app.Application
import androidx.room.Room
import com.leit.booktracker.feature_bookshelf.data.data_source.BookTrackerDatabase
import com.leit.booktracker.feature_bookshelf.data.repository.BookTrackerRepositoryImpl
import com.leit.booktracker.feature_bookshelf.domain.repository.BookTrackerRepository
import com.leit.booktracker.feature_bookshelf.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesBookTrackerDatabase(app:Application):BookTrackerDatabase{
        return Room.databaseBuilder(
            app,
            BookTrackerDatabase::class.java,
            BookTrackerDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideBookTrackerRepository(db:BookTrackerDatabase):BookTrackerRepository{
        return BookTrackerRepositoryImpl(db.bookTrackerDao)
    }

    @Provides
    @Singleton
    fun provideBookShelfUseCases(repository: BookTrackerRepository):BookShelfUseCases{
        return BookShelfUseCases(
            getBooks = GetBooks(repository),
            getSingleBook = GetSingleBook(repository),
            insertBook = InsertBook(repository),
            deleteBook = DeleteBook(repository)
        )
    }

    @Provides
    @Singleton
    fun provideDetailUseCases(repository: BookTrackerRepository):DetailUseCases{
        return DetailUseCases(
            getSingleBook = GetSingleBook(repository),
            insertReadingSession = InsertReadingSession(repository),
            getNotes = GetNotes(repository),
            calculatePagesToDate = CalculatePagesToDate(),
            getReadingSessionsByBookId = GetReadingSessionsByBookId(repository)
        )
    }
}
