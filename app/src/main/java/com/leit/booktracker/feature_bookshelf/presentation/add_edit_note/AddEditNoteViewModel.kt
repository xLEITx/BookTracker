package com.leit.booktracker.feature_bookshelf.presentation.add_edit_note

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leit.booktracker.R
import com.leit.booktracker.feature_bookshelf.domain.model.InvalidNoteException
import com.leit.booktracker.feature_bookshelf.domain.model.Note
import com.leit.booktracker.feature_bookshelf.domain.use_case.AddEditNoteUseCases
import com.leit.booktracker.feature_bookshelf.presentation.util.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import javax.inject.Inject


@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val addEditNoteUseCases: AddEditNoteUseCases,
    @ApplicationContext context: Context,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _noteId:Int? = null

    private val _title = mutableStateOf(
        TextFieldState(
            hint = context.getString(R.string.enter_title_hint)
        )
    )
    val title: State<TextFieldState> = _title

    private val _content = mutableStateOf(
        TextFieldState(
            hint = context.getString(R.string.write_your_note_hint)
        )
    )
    val content: State<TextFieldState> = _content


    private val _bookId = mutableStateOf(-1)

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            Log.d("ADD_EDIT_VM", noteId.toString())
            if(noteId != -1){
                viewModelScope.launch {
                    addEditNoteUseCases.getSingleNote(noteId)?.also {note ->
                        _noteId = note.Id!!
                        _title.value = title.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _content.value = content.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _bookId.value = note.bookId
                    }
                }

            }else{
                savedStateHandle.get<Int>("bookId")?.let { bookId ->
                    _bookId.value = bookId
                }
            }
        }

    }


    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _title.value = title.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteEvent.ChangeTitleFocus -> {
                _title.value = title.value.copy(
                    isHintVisible = !event.focusState.isFocused && title.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.EnteredContent -> {
                _content.value = content.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteEvent.ChangeContentFocus -> {
                _content.value = content.value.copy(
                    isHintVisible = !event.focusState.isFocused && content.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {

                        addEditNoteUseCases.insertNote(
                            Note(
                                Id = _noteId,
                                title = title.value.text,
                                content = content.value.text,
                                timestamp = ZonedDateTime.now().toEpochSecond() * 1000,
                                bookId = _bookId.value,
                            )
                        )
                        _eventFlow.emit(UiEvent.NavigateUp)

                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(e.message ?: "Couldn't save note")
                        )
                    }
                }
            }
            is AddEditNoteEvent.DeleteNote ->{
                viewModelScope.launch {
                    if (_noteId != -1 && _noteId != null){
                        addEditNoteUseCases.deleteNoteById(_noteId!!)
                        _eventFlow.emit(UiEvent.NavigateUp)
                    }else{
                        _eventFlow.emit(UiEvent.ShowSnackBar("The note wasn't saved yet."))
                    }
                }
            }

        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        object NavigateUp : UiEvent()
    }

}