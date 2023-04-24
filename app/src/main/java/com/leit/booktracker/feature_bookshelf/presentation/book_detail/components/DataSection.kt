package com.leit.booktracker.feature_bookshelf.presentation.book_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leit.booktracker.R
import com.leit.booktracker.feature_bookshelf.domain.model.Book
import com.leit.booktracker.ui.theme.BookTrackerTheme

@Composable
fun DataSection(
    book: Book,
    modifier: Modifier = Modifier
){
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        shape = RoundedCornerShape(5.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text =  book.title,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = stringResource(id = R.string.author) + ": ${book.author}")
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = stringResource(id = R.string.type) + ": ${book.type}")
            }

            Text(
                text = stringResource(id = R.string.status) + ": ${book.status}",
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Preview
@Composable
fun DataSectionPreview(){
    BookTrackerTheme {
        DataSection(
            book = Book(
                title = "Harry Potter",
                author = "J.K Rowling",
                type = "Fantasy",
                status = "In progress",
                pagesCount = 5
            )
        )
    }
}