package com.leit.booktracker.feature_bookshelf.presentation.bookshelf

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leit.booktracker.feature_bookshelf.domain.model.Book
import com.leit.booktracker.feature_bookshelf.domain.use_case.BookShelfUseCases
import com.leit.booktracker.feature_bookshelf.domain.util.BookOrder
import com.leit.booktracker.feature_bookshelf.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookShelfViewModel @Inject constructor(
    private val bookShelfUseCases:BookShelfUseCases
): ViewModel() {

    private val _state = mutableStateOf(BookShelfState())
    val state: State<BookShelfState> = _state

    private var recentlyDeletedBook: Book? = null

    private var getBooksJob: Job? = null

    init {
        getBooks(BookOrder.Status(OrderType.Descending))
    }

    fun onEvent(event: BookShelfEvent){
        when(event){
            is BookShelfEvent.Order ->{
                if(state.value.bookOrder::class == event.bookOrder::class &&
                        state.value.bookOrder.orderType == event.bookOrder.orderType){
                    return
                }
                getBooks(event.bookOrder)
            }
            is BookShelfEvent.DeleteBook ->{
                viewModelScope.launch{
                    bookShelfUseCases.deleteBook(event.book)
                    recentlyDeletedBook = event.book
                }
            }
            is BookShelfEvent.ChangeStatus ->{
                viewModelScope.launch {
                   bookShelfUseCases.insertBook(
                        event.book.copy(status = event.status)
                   )
                }
            }
            is BookShelfEvent.RestoreBook ->{
                viewModelScope.launch {
                    bookShelfUseCases.insertBook(recentlyDeletedBook?: return@launch)
                    recentlyDeletedBook = null
                }
            }
            is BookShelfEvent.ToggleOrderSection ->{
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getBooks(bookOrder: BookOrder){
        getBooksJob?.cancel()

        getBooksJob = bookShelfUseCases.getBooks(bookOrder)
            .onEach {books ->
                _state.value = state.value.copy(
                    books = books,
                    bookOrder = bookOrder
                )
            }.launchIn(viewModelScope)
    }
}