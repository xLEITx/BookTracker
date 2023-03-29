package com.leit.booktracker.ui.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leit.booktracker.R
import com.leit.booktracker.ui.theme.BookTrackerTheme
import com.leit.booktracker.ui.theme.blueA700
import com.leit.booktracker.ui.theme.greenA700


// TODO: Change the status types in when and if statements to match the data class, maybe use some enum


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListItem(
    title:String,
    status:String,
    author:String,
    onIconButtonClick:() -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes cover:Int? = null,
){
    Card(modifier = modifier.padding(horizontal = 8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Image(
                //TODO: Add default image for cover
                painter = painterResource(id = cover ?: R.drawable.ic_launcher_background),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = when(status){
                    "In progress" -> Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .border(width = 2.dp, color = greenA700, shape = CircleShape)

                    "In wishlist" -> Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .border(width = 2.dp, color = blueA700, shape = CircleShape)

                    else -> Modifier
                        .size(48.dp)
                        .clip(CircleShape)

                }

            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(text = title, style = MaterialTheme.typography.headlineSmall)
                Text(text = author, style = MaterialTheme.typography.labelMedium)
            }

            if (status == "In wishlist"){
                IconButton(onClick = onIconButtonClick) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "")
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, widthDp = 360)
fun BookListItemPreview(){
    BookTrackerTheme {
        BookListItem(title = "Harry Potter", status = "In wishlist", author="J.K. Rowling", cover = R.drawable.harry, onIconButtonClick = {})
    }
}

@Composable
@Preview(showBackground = true, widthDp = 360)
fun BookListItemStackPreview(){
    BookTrackerTheme {
        Column(modifier = Modifier) {
            BookListItem(title = "Harry Potter", status = "In wishlist", author="J.K. Rowling", cover = R.drawable.harry, onIconButtonClick = {})
            Spacer(modifier = Modifier.height(8.dp))
            BookListItem(title = "Bot", status = "In progress", author="Max Kidruk", onIconButtonClick = {})
            Spacer(modifier = Modifier.height(8.dp))
            BookListItem(title = "Tower", status = "On boolshelf", author="King", onIconButtonClick = {})
        }

    }
}