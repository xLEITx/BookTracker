package com.leit.booktracker.feature_bookshelf.presentation.add_edit_book

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leit.booktracker.R
import com.leit.booktracker.feature_bookshelf.domain.model.Book
import com.leit.booktracker.feature_bookshelf.domain.model.InvalidBookException
import com.leit.booktracker.feature_bookshelf.domain.use_case.BookShelfUseCases
import com.leit.booktracker.feature_bookshelf.presentation.util.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditBookViewModel @Inject constructor(
    private val bookShelfUseCases: BookShelfUseCases,
    @ApplicationContext context: Context,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _bookTitle = mutableStateOf(
        TextFieldState(
            hint = context.getString(R.string.enter_title_hint)
        )
    )
    val bookTitle: State<TextFieldState> = _bookTitle

    private val _bookAuthor = mutableStateOf(
        TextFieldState(
            hint = context.getString(R.string.enter_author_hint)
        )
    )
    val bookAuthor: State<TextFieldState> = _bookAuthor

    private val _bookType = mutableStateOf("")
    val bookType: State<String> = _bookType

    private val _bookStatus = mutableStateOf("")
    val bookStatus: State<String> = _bookStatus

    private val _bookPages = mutableStateOf(0)
    val bookPages: State<Int> = _bookPages

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow

    private var currentBookId: Int? = null

    var isNewBook = true

    init {
        savedStateHandle.get<Int>("bookId")?.let {bookId ->
            if (bookId != -1){
                viewModelScope.launch {
                    bookShelfUseCases.getSingleBook(bookId)?.also { book->
                        currentBookId = book.bookId
                        _bookTitle.value = bookTitle.value.copy(
                            text = book.title,
                            isHintVisible = false
                        )
                        _bookAuthor.value = bookAuthor.value.copy(
                            text = book.author,
                            isHintVisible = false
                        )
                        _bookType.value = book.type
                        _bookStatus.value = book.status
                        _bookPages.value = book.pagesCount
                    }
                    isNewBook = false
                }
            }
        }
    }



    fun onEvent(event: AddEditBookEvent) {
        when (event) {
            is AddEditBookEvent.EnteredTitle -> {
                _bookTitle.value = bookTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditBookEvent.ChangeTitleFocus -> {
                _bookTitle.value = bookTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && bookTitle.value.text.isBlank()
                )
            }
            is AddEditBookEvent.EnteredAuthor -> {
                _bookAuthor.value = bookAuthor.value.copy(
                    text = event.value
                )
            }
            is AddEditBookEvent.ChangeAuthorFocus -> {
                _bookAuthor.value = bookAuthor.value.copy(
                    isHintVisible = !event.focusState.isFocused && bookAuthor.value.text.isBlank()
                )
            }

            is AddEditBookEvent.SelectedType -> {
                _bookType.value = event.value
            }

            is AddEditBookEvent.SelectedStatus -> {
                _bookStatus.value = event.value
            }

            is AddEditBookEvent.ChangePages -> {
                _bookPages.value = event.value
            }

            is AddEditBookEvent.SaveBook -> {

                viewModelScope.launch {

                    try {
                        bookShelfUseCases.insertBook(
                            Book(
                                bookId = currentBookId,
                                title = bookTitle.value.text,
                                author = bookAuthor.value.text,
                                type = bookType.value,
                                status = bookStatus.value,
                                pagesCount = bookPages.value
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveBook)
                    } catch (e: InvalidBookException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                e.message ?: "Couldn't save book"
                            )
                        )

                    }
                }
            }
        }
    }

    sealed class UiEvent{
        data class ShowSnackBar(val message:String):UiEvent()
        object SaveBook:UiEvent()
    }
}