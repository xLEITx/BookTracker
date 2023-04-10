package com.leit.booktracker.feature_bookshelf.presentation.bookshelf.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leit.booktracker.ui.theme.BookTrackerTheme

@Composable
fun DefaultRadioButton(
    text:String,
    selected:Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
){
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = selected, onClick = onSelect)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.bodyLarge)
    }

}

@Preview(showBackground = true)
@Composable
private fun DefaultRadioButtonPreview(){
    BookTrackerTheme {
        DefaultRadioButton(text = "Status", selected = true, onSelect = {})
    }
}