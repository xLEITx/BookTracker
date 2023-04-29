package com.leit.booktracker.feature_bookshelf.presentation.book_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.leit.booktracker.R
import com.leit.booktracker.ui.theme.BookTrackerTheme

@Composable
fun PagesSection(
    currentPages:Int,
    maxPages:Int,
    onIncrease:() -> Unit,
    isOnIncreaseEnabled:Boolean,
    onDecrease:() -> Unit,
    isOnDecreaseEnabled:Boolean,
    onSave:() -> Unit,
    modifier: Modifier = Modifier
){
    Surface(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
        ) {
            
            IconButton(
                onClick = {},
                enabled = false
            ) {
                //This is just a stub so that the row is centered on the text (Is it stupid? Yes. Does it work? Yes.)
            }
            
            IconButton(
                onClick = onDecrease,
                enabled = isOnDecreaseEnabled
            ) {
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = stringResource(R.string.decrease_pages_count_imgbtn)
                )
            }

            Text(
                text = "$currentPages/$maxPages",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            IconButton(
                onClick = onIncrease,
                enabled = isOnIncreaseEnabled
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.increase_pages_count_ingbtn)
                )
            }
            IconButton(
                onClick = onSave,
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = stringResource(R.string.increase_pages_count_ingbtn)
                )
            }
        }

    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun PagesSectionPreview(){
    BookTrackerTheme {
        PagesSection(
            currentPages = 5,
            maxPages = 105,
            onIncrease = { },
            isOnIncreaseEnabled = true ,
            onDecrease = {  },
            isOnDecreaseEnabled = true,
            onSave = {}
        )
    }
}