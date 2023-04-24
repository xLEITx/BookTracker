package com.leit.booktracker.feature_bookshelf.presentation.book_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leit.booktracker.feature_bookshelf.domain.model.Book
import com.leit.booktracker.feature_bookshelf.domain.model.Note
import com.leit.booktracker.feature_bookshelf.domain.model.ReadingSession
import com.leit.booktracker.feature_bookshelf.domain.use_case.DetailUseCases
import com.leit.booktracker.feature_bookshelf.domain.util.NoteOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val detailUseCases:DetailUseCases,
    savedStateHandle: SavedStateHandle
):ViewModel() {

    private val _book = mutableStateOf(Book(null, "", "", "","",0))
    val book: State<Book> = _book

    private val _readPages = mutableStateOf(0)
    val readPages:State<Int> = _readPages

    private val _initialReadPages = mutableStateOf(0)
    val initialReadPages = _initialReadPages

    private val _chosenDate = mutableStateOf(LocalDate.now())
    val chosenDate:State<LocalDate> = _chosenDate

    private var currentBookId: Int? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow

    private var getNotesJob: Job? = null

    private val _notes = mutableStateOf(listOf<Note>())
    val notes:State<List<Note>> = _notes

    init {
        savedStateHandle.get<Int>("bookId")?.let {bookId ->
            if (bookId != -1){
                viewModelScope.launch {

                    detailUseCases.getReadingSessionsByBookId(bookId).forEach {bookWithReadingSessions ->
                        bookWithReadingSessions.readingSessions.forEach { readingSession ->
                            _initialReadPages.value += readingSession.pages
                        }
                        _book.value = bookWithReadingSessions.book
                    }

                }
                _readPages.value = _initialReadPages.value
            }
            else{
                //TODO: return to Bookshelf Screen
            }
        }
    }

    init {
        getNotes(noteOrder = NoteOrder.Title)
    }

    fun onEvent(event: BookDetailEvent){
        when(event){
            is BookDetailEvent.ChangeReadPages ->{
                _readPages.value = event.value
            }
            is BookDetailEvent.SaveReadingSession ->{
                viewModelScope.launch {
                    detailUseCases.insertReadingSession(
                        ReadingSession(
                            pages = readPages.value - initialReadPages.value,
                            timestamp = ZonedDateTime.now().toEpochSecond() * 1000,
                            bookId = book.value.bookId!!
                        )
                    )
                }
            }
            is BookDetailEvent.CalculatePages ->{
                viewModelScope.launch {
                    eventFlow.emit(
                        UiEvent.ShowSnackBar(
                            detailUseCases.calculatePagesToDate(
                                currentDate = LocalDate.now(),
                                chosenDate = event.chosenDate,
                                pages = book.value.pagesCount - readPages.value
                            ).toString()
                        )
                    )
                }
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder){
        getNotesJob?.cancel()

        getNotesJob = detailUseCases.getNotes(noteOrder)
            .onEach {books ->
                _notes.value = books
            }.launchIn(viewModelScope)
    }

    sealed class UiEvent{
        data class ShowSnackBar(val message:String):UiEvent()
    }

}