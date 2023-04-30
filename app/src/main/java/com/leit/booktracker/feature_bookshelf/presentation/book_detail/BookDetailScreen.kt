package com.leit.booktracker.feature_bookshelf.presentation.book_detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.leit.booktracker.R
import com.leit.booktracker.feature_bookshelf.presentation.book_detail.components.DataSection
import com.leit.booktracker.feature_bookshelf.presentation.book_detail.components.DateCalcSection
import com.leit.booktracker.feature_bookshelf.presentation.book_detail.components.NoteItem
import com.leit.booktracker.feature_bookshelf.presentation.book_detail.components.PagesSection
import com.leit.booktracker.feature_bookshelf.presentation.util.Screen
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(
    navController: NavController,
    viewModel: BookDetailViewModel = hiltViewModel()
){

    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val dateState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Input,
        initialSelectedDateMillis = ZonedDateTime.now().toEpochSecond() * 1000
    )


    LaunchedEffect(key1 = true ){
        viewModel.eventFlow.collectLatest { event ->

            when(event){
                is BookDetailViewModel.UiEvent.ShowSnackBar ->{
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = event.message,
                            duration = SnackbarDuration.Short
                        )
                    }
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
                Icon(imageVector = Icons.Default.Edit, contentDescription = stringResource(R.string.edit_book))
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState){data ->
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

            DataSection(book = state.book)

            PagesSection(
                currentPages = state.readPages,
                maxPages = state.book.pagesCount,
                onIncrease = { viewModel.onEvent(BookDetailEvent.ChangeReadPages(state.readPages + 1)) },
                isOnIncreaseEnabled = state.readPages < state.book.pagesCount ,
                onDecrease = { viewModel.onEvent(BookDetailEvent.ChangeReadPages(state.readPages - 1))},
                isOnDecreaseEnabled = state.readPages > state.initialReadPages ,
                onSave = { viewModel.onEvent(BookDetailEvent.SaveReadingSession) }
            )

            DateCalcSection(
                state = dateState,
                onCalculate = {
                    if(dateState.selectedDateMillis != null){
                        viewModel.onEvent(BookDetailEvent.CalculatePages(
                            LocalDateTime.ofInstant(
                                Instant.ofEpochMilli(dateState.selectedDateMillis!!),
                                TimeZone.getDefault().toZoneId())
                        ))
                    }else{
                        scope.launch {
                            viewModel.eventFlow.emit(BookDetailViewModel.UiEvent.ShowSnackBar("Date not picked"))
                        }
                    }
                }
            )

            LazyColumn(Modifier.fillMaxSize()){
                items(state.notes){note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier.clickable {
                            //TODO: Transaction on the note edit screen
                        }
                    )
                }
            }

        }


    }

}