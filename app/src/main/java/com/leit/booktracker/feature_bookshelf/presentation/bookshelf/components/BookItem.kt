package com.leit.booktracker.feature_bookshelf.presentation.bookshelf.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leit.booktracker.R
import com.leit.booktracker.feature_bookshelf.domain.model.Book
import com.leit.booktracker.feature_bookshelf.domain.util.BookStatus
import com.leit.booktracker.ui.theme.BookTrackerTheme

@Composable
fun BookItem(
    book: Book,
    onAddButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Card(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {


            BookCircleImage(bookStatus = book.status)

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                )
                Text(
                    text = book.author,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier
                )
            }

            IconButton(onClick = onDeleteButtonClick) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = stringResource(R.string.delete_iconbtn_desc))
            }

            if (book.status == BookStatus.IN_WISHLIST){
                IconButton(onClick = onAddButtonClick) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(R.string.add_book_on_bookshelf_icnbtn_desc))
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun BookItemPreview(){
    BookTrackerTheme {
        BookItem(Book(1,"Voroshilovgrad", "S. Zhadan", "IDK", BookStatus.IN_WISHLIST,54), {}, {})
    }
}
