package com.leit.booktracker.feature_bookshelf.presentation.bookshelf

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.leit.booktracker.R
import com.leit.booktracker.feature_bookshelf.domain.util.BookStatus
import com.leit.booktracker.feature_bookshelf.presentation.bookshelf.components.BookItem
import com.leit.booktracker.feature_bookshelf.presentation.bookshelf.components.OrderSection
import kotlinx.coroutines.launch

@Composable
fun BookShelfScreen(
    navController: NavController,
    viewModel: BookShelfViewModel = hiltViewModel()
){
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val snackbarStrings = listOf(
        stringResource(R.string.book_deleted_snackbar_msg),
        stringResource(R.string.undo)
    )
    
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(R.string.add_note_fbutton_desc))
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(text = stringResource(R.string.bookshelf_screen_title), style = MaterialTheme.typography.headlineLarge)
                IconButton(onClick = { viewModel.onEvent(BookShelfEvent.ToggleOrderSection) }) {
                    Icon(imageVector = Icons.Default.Sort, contentDescription = stringResource(R.string.sort))
                }
            }

            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    bookOrder = state.bookOrder,
                    onOrderChange = {
                        viewModel.onEvent(BookShelfEvent.Order(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize(), ){
                items(state.books){ book ->
                    BookItem(
                        book = book,
                        onAddButtonClick = {
                            viewModel.onEvent(BookShelfEvent.ChangeStatus(book = book, status = BookStatus.ON_BOOKSHELF))
                        },
                        onDeleteButtonClick = {
                            viewModel.onEvent(BookShelfEvent.DeleteBook(book))
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = snackbarStrings[0],
                                    actionLabel = snackbarStrings[1]
                                )

                                if(result == SnackbarResult.ActionPerformed){
                                    viewModel.onEvent(BookShelfEvent.RestoreBook)
                                }
                            }
                        },
                        modifier = Modifier.clickable {
                            //TODO:Implement transaction to the detail screen
                        }
                    )
                    Spacer(Modifier.height(4.dp))
                    
                }
            }

        }
    }
}