package com.leit.booktracker.feature_bookshelf.presentation.bookshelf.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.leit.booktracker.R
import com.leit.booktracker.feature_bookshelf.domain.util.BookFilter

@Composable
fun FilterChipSection(
    bookFilter: BookFilter,
    onInProgress:() -> Unit,
    onInWishlist:() -> Unit,
    onOnBookshelf:() -> Unit,
    onFinished:() -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .horizontalScroll(rememberScrollState())

    ) {
        AssistChip(
            onClick = onInProgress,
            label = {
                Text(
                    text = stringResource(R.string.in_progress_status)
                )
            },
            colors = if (bookFilter.isInProgress)
                AssistChipDefaults.assistChipColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    else AssistChipDefaults.assistChipColors()
        )
        Spacer(modifier = Modifier.width(4.dp))
        AssistChip(
            onClick = onInWishlist,
            label = {
                Text(
                    text = stringResource(R.string.in_wishlist)
                )
            },
            colors = if (bookFilter.isInWishlist)
                AssistChipDefaults.assistChipColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            else AssistChipDefaults.assistChipColors()
        )
        Spacer(modifier = Modifier.width(4.dp))
        AssistChip(
            onClick = onOnBookshelf,
            label = {
                Text(
                    text = stringResource(R.string.on_bookshelf)
                )
            },
            colors = if (bookFilter.isOnBookshelf)
                AssistChipDefaults.assistChipColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            else AssistChipDefaults.assistChipColors()
        )
        Spacer(modifier = Modifier.width(4.dp))
        AssistChip(
            onClick = onFinished,
            label = {
                Text(
                    text = stringResource(R.string.finished)
                )
            },
            colors = if (bookFilter.isFinished)
                AssistChipDefaults.assistChipColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            else AssistChipDefaults.assistChipColors()
        )
    }
}