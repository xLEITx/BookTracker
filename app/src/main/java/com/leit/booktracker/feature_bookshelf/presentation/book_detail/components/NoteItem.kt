package com.leit.booktracker.feature_bookshelf.presentation.book_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leit.booktracker.feature_bookshelf.domain.model.Note
import com.leit.booktracker.ui.theme.BookTrackerTheme
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier
){
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer ,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Column(Modifier.weight(2f)) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.labelMedium
                )
                Divider(thickness = 0.5.dp , color = MaterialTheme.colorScheme.onPrimaryContainer)
                Text(
                    text = note.content.take(30) + "...",
                    style = MaterialTheme.typography.labelSmall
                )
                
            }
            //TODO: Normal date/time format
            Text(
                text = Instant.ofEpochMilli(note.timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime().toString(),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.weight(3f)
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun NoteItemPreview(){
    BookTrackerTheme {
        NoteItem(note = Note(
            title = "Hello",
            content = "Absolutely random words apple lab-rat sunday hello it`s me MARIO!!! or WaRiO? ",
            timestamp = ZonedDateTime.now().toEpochSecond() * 1000,
            bookId = 5
        ))
    }
}