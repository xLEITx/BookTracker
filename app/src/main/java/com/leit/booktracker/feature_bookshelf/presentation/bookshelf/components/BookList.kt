package com.leit.booktracker.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leit.booktracker.R
import com.leit.booktracker.feature_bookshelf.domain.model.Book
import com.leit.booktracker.feature_bookshelf.domain.model.BookStatus
import com.leit.booktracker.ui.theme.BookTrackerTheme

@Composable
fun BookList(
    booksList: List<Book>,
    modifier: Modifier = Modifier
){
    LazyColumn(
        modifier = modifier.padding(vertical = 6.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ){
        items(booksList){
            BookListItem(title = it.title, status = it.status, author = it.author , onIconButtonClick = {} , cover = it.cover)
        }
    }
    
}

val booksList = listOf(
    Book("Harry Potter", "J.K Rowling", R.drawable.harry, BookStatus.IN_PROGRESS, "Young adult"),
    Book("White Snake", "Kipling", null, BookStatus.IN_WISHLIST, "Sci-fi"),
    Book("Tower", "King", null, BookStatus.ON_BOOKSHELF, "Noir"),
    Book("Harry Potter", "J.K Rowling", null, BookStatus.IN_PROGRESS, "Sci-fi"),
    Book("Harry Potter", "J.K Rowling", R.drawable.harry, BookStatus.IN_PROGRESS, "Young adult"),

)

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 200)
@Composable
fun BookListPreview(){
    BookTrackerTheme {
        BookList(booksList = booksList)
    }
}