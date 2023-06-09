package com.leit.booktracker.feature_bookshelf.presentation.bookshelf

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import com.leit.booktracker.feature_bookshelf.presentation.util.FilterOption
import com.leit.booktracker.feature_bookshelf.presentation.util.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookShelfScreen(
    navController: NavController,
    viewModel: BookShelfViewModel = hiltViewModel()
) {
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
                navController.navigate(Screen.BookAddEditScreen.route)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_book_fbutton_desc)
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
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.bookshelf_screen_title),
                    style = MaterialTheme.typography.headlineLarge
                )
                IconButton(onClick = { viewModel.onEvent(BookShelfEvent.ToggleOrderSection) }) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = stringResource(R.string.sort)
                    )
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
                        .padding(16.dp),
                    bookOrder = state.bookOrder,
                    onOrderChange = {
                        viewModel.onEvent(BookShelfEvent.Order(it))
                    }
                )
            }



            LazyRow(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                val filterOptions = listOf(
                    FilterOption(
                        stringRes = R.string.in_progress_status,
                        isSelected = state.bookFilter.isInProgress,
                        onClick = {
                            viewModel.onEvent(
                                BookShelfEvent.FilterChange(
                                    state.bookFilter.copy(
                                        isInProgress = !state.bookFilter.isInProgress
                                    )
                                )
                            )
                        }
                    ),
                    FilterOption(
                        stringRes = R.string.in_wishlist,
                        isSelected = state.bookFilter.isInWishlist,
                        onClick = {
                            viewModel.onEvent(
                                BookShelfEvent.FilterChange(
                                    state.bookFilter.copy(
                                        isInWishlist = !state.bookFilter.isInWishlist
                                    )
                                )
                            )
                        }
                    ),
                    FilterOption(
                        stringRes = R.string.on_bookshelf,
                        isSelected = state.bookFilter.isOnBookshelf,
                        onClick = {
                            viewModel.onEvent(
                                BookShelfEvent.FilterChange(
                                    state.bookFilter.copy(
                                        isOnBookshelf = !state.bookFilter.isOnBookshelf
                                    )
                                )
                            )
                        }
                    ),
                    FilterOption(
                        stringRes = R.string.finished,
                        isSelected = state.bookFilter.isFinished,
                        onClick = {
                            viewModel.onEvent(
                                BookShelfEvent.FilterChange(
                                    state.bookFilter.copy(
                                        isFinished = !state.bookFilter.isFinished
                                    )
                                )
                            )
                        }
                    )
                )


                items(filterOptions) { option ->
                    FilterChip(
                        selected = option.isSelected,
                        onClick = option.onClick,
                        label = { Text(text = stringResource(id = option.stringRes)) }
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp)
            ) {
                items(
                    items = state.books,
                    key = { it.bookId!! }
                ) { book ->
                    BookItem(
                        book = book,
                        onAddButtonClick = {
                            viewModel.onEvent(
                                BookShelfEvent.ChangeStatus(
                                    book = book,
                                    status = BookStatus.ON_BOOKSHELF
                                )
                            )
                        },
                        onDeleteButtonClick = {
                            viewModel.onEvent(BookShelfEvent.DeleteBook(book))
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = snackbarStrings[0],
                                    actionLabel = snackbarStrings[1],
                                    duration = SnackbarDuration.Long
                                )

                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(BookShelfEvent.RestoreBook)
                                }
                            }
                        },
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.BookDetailScreen.route + "?bookId=${book.bookId}")
                        }
                    )
                    Spacer(Modifier.height(4.dp))

                }
            }

        }
    }
}