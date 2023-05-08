package com.leit.booktracker.feature_bookshelf.presentation.book_detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.leit.booktracker.R
import com.leit.booktracker.feature_bookshelf.domain.util.NoteOrder
import com.leit.booktracker.feature_bookshelf.presentation.book_detail.components.DataSection
import com.leit.booktracker.feature_bookshelf.presentation.book_detail.components.NoteItem
import com.leit.booktracker.feature_bookshelf.presentation.book_detail.components.PagesSection
import com.leit.booktracker.feature_bookshelf.presentation.util.Screen
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@Composable
fun BookDetailScreen(
    navController: NavController,
    viewModel: BookDetailViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var isSortedByDate by remember {
        mutableStateOf(true)
    }


    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->

            when (event) {
                is BookDetailViewModel.UiEvent.ShowSnackBar -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = event.message,
                            duration = SnackbarDuration.Short
                        )
                    }
                }
                is BookDetailViewModel.UiEvent.NavigateUp ->{
                    navController.navigateUp()
                }
            }

        }
    }


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.BookAddEditScreen.route + "?bookId=${state.book.bookId}")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_book)
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            DataSection(
                book = state.book,
                sideEffect = {
                    viewModel.getBook(viewModel.state.value.book.bookId)
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            PagesSection(
                currentPages = state.readPages,
                maxPages = state.book.pagesCount,
                onIncrease = { viewModel.onEvent(BookDetailEvent.ChangeReadPages(state.readPages + 1)) },
                isOnIncreaseEnabled = state.readPages < state.book.pagesCount,
                onDecrease = { viewModel.onEvent(BookDetailEvent.ChangeReadPages(state.readPages - 1)) },
                isOnDecreaseEnabled = state.readPages > 0,
                onSave = { viewModel.onEvent(BookDetailEvent.SaveReadingSession) }
            )


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.your_notes),
                    style = MaterialTheme.typography.headlineLarge
                )

                Row {
                    IconButton(onClick = {
                        if (isSortedByDate){
                            viewModel.onEvent(BookDetailEvent.ChangeNoteOrder(NoteOrder.Title))

                        }else{
                            viewModel.onEvent(BookDetailEvent.ChangeNoteOrder(NoteOrder.Date))
                        }
                        isSortedByDate = !isSortedByDate
                    }) {
                        if (isSortedByDate){
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = stringResource(R.string.change_sort_type)
                            )
                        }else{
                            Icon(
                                imageVector =  Icons.Default.Title,
                                contentDescription = stringResource(R.string.change_sort_type)
                            )
                        }

                    }

                    IconButton(onClick = {
                        navController.navigate(
                            Screen.AddEditNoteScreen.route + "?bookId=${state.book.bookId}"
                        )
                    }) {
                        Icon(
                            imageVector = Icons.Default.Note,
                            contentDescription = stringResource(R.string.add_note)
                        )
                    }
                }

            }

            LazyColumn(Modifier.fillMaxSize()) {
                items(viewModel.notes.value.notes) { note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .clickable {
                                navController.navigate(
                                    Screen.AddEditNoteScreen.route + "?bookId=-1&noteId=${note.Id}"
                                )
                            }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

        }


    }

}