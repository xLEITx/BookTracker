package com.leit.booktracker.feature_bookshelf.presentation.book_detail

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leit.booktracker.feature_bookshelf.domain.model.ReadingSession
import com.leit.booktracker.feature_bookshelf.domain.use_case.DetailUseCases
import com.leit.booktracker.feature_bookshelf.domain.util.NoteOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val detailUseCases: DetailUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(BookDetailState())
    val state: State<BookDetailState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow

    private var getNotesJob: Job? = null

    init {
        savedStateHandle.get<Int>("bookId")?.let { bookId ->
            viewModelScope.launch {
                if (bookId != -1) {
                    detailUseCases.getReadingSessionsByBookId(bookId)
                        .forEach { bookWithReadingSessions ->
                            bookWithReadingSessions.readingSessions.forEach { readingSession ->
                                _state.value = state.value.copy(
                                    initialReadPages = state.value.initialReadPages + readingSession.pages
                                )
                            }
                            _state.value = state.value.copy(
                                book = bookWithReadingSessions.book
                            )
                        }
                    _state.value = state.value.copy(
                        readPages = state.value.initialReadPages
                    )

                    getNotes(bookId = bookId, noteOrder = NoteOrder.Date)
                } else {
                    eventFlow.subscriptionCount.map {count -> count > 0}
                        .distinctUntilChanged()
                        .onEach { isActive ->
                            if (isActive) _eventFlow.emit(UiEvent.NavigateUp)
                        }.launchIn(viewModelScope)
                }
            }


        }
    }


    fun onEvent(event: BookDetailEvent) {
        when (event) {
            is BookDetailEvent.ChangeReadPages -> {
                _state.value = state.value.copy(
                    readPages = event.value
                )
            }

            is BookDetailEvent.SaveReadingSession -> {
                viewModelScope.launch {
                    detailUseCases.insertReadingSession(
                        ReadingSession(
                            pages = state.value.readPages - state.value.initialReadPages,
                            timestamp = ZonedDateTime.now().toEpochSecond() * 1000,
                            bookId = state.value.book.bookId!!
                        )
                    )
                    eventFlow.emit(UiEvent.ShowSnackBar("Saved"))
                    Log.d("DETAIL_VM", _eventFlow.subscriptionCount.value.toString())
                }
            }

        }
    }


    fun getBook(bookId: Int?) {
        if (bookId != null && bookId != -1) {
            viewModelScope.launch {
                _state.value = state.value.copy(
                    book = detailUseCases.getSingleBook(bookId)!!
                )
            }
        }
    }

    private fun getNotes(bookId: Int, noteOrder: NoteOrder) {
        getNotesJob?.cancel()

        getNotesJob = detailUseCases.getNotesByBookId(bookId, noteOrder)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }.launchIn(viewModelScope)
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        object NavigateUp : UiEvent()
    }

}