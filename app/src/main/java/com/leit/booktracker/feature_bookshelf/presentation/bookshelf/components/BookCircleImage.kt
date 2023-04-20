package com.leit.booktracker.feature_bookshelf.presentation.bookshelf.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.leit.booktracker.R
import com.leit.booktracker.feature_bookshelf.domain.util.BookStatus


@Composable
fun BookCircleImage(
    bookStatus:String
){
    Image(
        painter = painterResource(
            id = when(bookStatus){
                BookStatus.FINISHED -> R.drawable.book_image_stub_orange
                BookStatus.IN_PROGRESS -> R.drawable.book_image_stub_green
                BookStatus.IN_WISHLIST -> R.drawable.book_image_stub_blue
                else -> R.drawable.book_image_stub_brown
            }
        ),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)

    )
}