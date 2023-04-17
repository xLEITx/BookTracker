package com.leit.booktracker.feature_bookshelf.presentation.add_edit_book

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.leit.booktracker.feature_bookshelf.presentation.add_edit_book.components.PressedIconButton
import com.leit.booktracker.feature_bookshelf.presentation.add_edit_book.components.Spinner
import com.leit.booktracker.feature_bookshelf.presentation.util.StatusOptions
import com.leit.booktracker.feature_bookshelf.presentation.util.TypeOptions
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Thread.sleep

@Composable
fun BookAddEditScreen(
    navController: NavController,
    viewModel: BookAddEditViewModel = hiltViewModel()
) {

    val title = viewModel.bookTitle.value
    val author = viewModel.bookAuthor.value
    val type = viewModel.bookType.value
    val status = viewModel.bookStatus.value
    val pages = viewModel.bookPages.value

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is BookAddEditViewModel.UiEvent.ShowSnackBar -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = event.message,
                            duration = SnackbarDuration.Short
                        )
                    }
                }

                is BookAddEditViewModel.UiEvent.SaveBook -> {
                    navController.navigateUp()
                }
            }

        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.onEvent(AddEditBookEvent.SaveBook) }) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = stringResource(R.string.save_floatbtn)
                )
            }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
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
                .padding(16.dp),

            ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                if (viewModel.isNewBook) {
                    Text(
                        text = stringResource(R.string.add_new_book_header),
                        style = MaterialTheme.typography.headlineLarge
                    )
                } else {
                    Text(
                        text = stringResource(R.string.edit_book_header),
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                OutlinedTextField(
                    singleLine = true,
                    value = title.text,
                    onValueChange = {
                        viewModel.onEvent(AddEditBookEvent.EnteredTitle(it))
                    },
                    placeholder = {
                        Text(text = title.hint)
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    singleLine = true,
                    value = author.text,
                    onValueChange = {
                        viewModel.onEvent(AddEditBookEvent.EnteredAuthor(it))
                    },
                    placeholder = {
                        Text(text = author.hint)
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Spinner(
                    options = TypeOptions.options,
                    chosenOption = type,
                    onOptionChange = { viewModel.onEvent(AddEditBookEvent.SelectedType(it)) },
                    label = stringResource(R.string.choose_type),
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Spinner(
                    options = StatusOptions.options,
                    chosenOption = status,
                    label = stringResource(R.string.select_status),
                    onOptionChange = { viewModel.onEvent(AddEditBookEvent.SelectedStatus(it)) },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                )


                Text(
                    text = stringResource(R.string.book_pages),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(vertical = 25.dp)
                )


                Surface(shape = CircleShape, color = MaterialTheme.colorScheme.surfaceVariant) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        PressedIconButton(
                            enabled = pages > 0,
                            icon = Icons.Default.Remove,
                            iconDescription = stringResource(R.string.decrease_pages_count_imgbtn),
                            onPress = {
                                sleep(150)
                                viewModel.onEvent(
                                    AddEditBookEvent.ChangePages(
                                        value = pages - 1
                                    )
                                )

                            },
                            onDispose = {}
                        )


                        Text(
                            text = pages.toString(),
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        PressedIconButton(
                            enabled = true,
                            icon = Icons.Default.Add,
                            iconDescription = stringResource(R.string.increase_pages_count_ingbtn) ,
                            onPress = {
                                sleep(150)
                                viewModel.onEvent(
                                    AddEditBookEvent.ChangePages(
                                        value = pages + 1
                                    )
                                )
                            },
                            onDispose = {}
                        )

                    }
                }

            }


        }

    }


}
