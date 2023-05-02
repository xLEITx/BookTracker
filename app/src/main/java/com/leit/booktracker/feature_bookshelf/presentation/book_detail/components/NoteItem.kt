package com.leit.booktracker.feature_bookshelf.presentation.book_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import java.time.format.DateTimeFormatter

@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {

            Text(
                text = note.title,
                style = MaterialTheme.typography.headlineSmall
            )
            Divider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.onPrimaryContainer)
            Text(
                text = note.content.take(150) + "...",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = Instant.ofEpochMilli(note.timestamp)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
                    .format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    ),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun NoteItemPreview() {
    BookTrackerTheme {
        NoteItem(
            note = Note(
                title = "Hello",
                content = "Absolutely random words apple lab-rat sunday hello it`s me MARIO!!! or WaRiO?" +
                        "Absolutely random words apple lab-rat sunday hello it`s me MARIO!!! or WaRiO?" +
                        "Absolutely random words apple lab-rat sunday hello it`s me MARIO!!! or WaRiO?" +
                        "Absolutely random words apple lab-rat sunday hello it`s me MARIO!!! or WaRiO?",
                timestamp = ZonedDateTime.now().toEpochSecond() * 1000,
                bookId = 5
            )
        )
    }
}