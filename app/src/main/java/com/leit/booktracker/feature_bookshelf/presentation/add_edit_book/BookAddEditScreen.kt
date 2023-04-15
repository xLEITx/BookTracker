package com.leit.booktracker.feature_bookshelf.presentation.add_edit_book

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.leit.booktracker.R
import com.leit.booktracker.feature_bookshelf.domain.model.Book
import com.leit.booktracker.feature_bookshelf.domain.util.BookStatus
import com.leit.booktracker.feature_bookshelf.presentation.add_edit_book.components.Spinner
import com.leit.booktracker.feature_bookshelf.presentation.util.StatusOptions
import com.leit.booktracker.feature_bookshelf.presentation.util.TypeOptions

@Composable
fun BookAddEditScreen(
    navController: NavController,
    viewModel: BookAddEditViewModel = hiltViewModel()
){

    val title = viewModel.bookTitle.value
    val author = viewModel.bookAuthor.value
    val type = viewModel.bookType.value
    val status = viewModel.bookStatus.value
    val pages = viewModel.bookPages.value

    val snackbarHostState = remember { SnackbarHostState() }
    
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {viewModel.onEvent(AddEditBookEvent.SaveBook)}) {
                Icon(imageVector = Icons.Default.Save, contentDescription = stringResource(R.string.save_floatbtn))
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            TextField(
                value = title.text,
                onValueChange = {
                    viewModel.onEvent(AddEditBookEvent.EnteredTitle(it))
                },
                placeholder = {
                        Text(text = title.hint)
                },
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = author.text,
                onValueChange = {
                    viewModel.onEvent(AddEditBookEvent.EnteredAuthor(it))
                },
                placeholder = {
                    Text(text = author.hint)
                },
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Spinner(
                options = TypeOptions.options,
                chosenOption = type,
                onOptionChange = { viewModel.onEvent(AddEditBookEvent.SelectedType(it)) },
                label = stringResource(R.string.choose_type)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Spinner(
                options = StatusOptions.options,
                chosenOption = status,
                label = stringResource(R.string.select_status),
                onOptionChange = {viewModel.onEvent(AddEditBookEvent.SelectedStatus(it))}
            )

            //TODO:pages








        }

    }


}
