package com.leit.booktracker.feature_bookshelf.presentation.book_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leit.booktracker.R
import com.leit.booktracker.ui.theme.BookTrackerTheme
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateCalcSection(
    state:DatePickerState,
    onCalculate: () -> Unit,
    modifier:Modifier = Modifier
){
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier.padding(8.dp)
    ) {
        
        DatePicker(
            state = state,
            dateValidator = {
                it >= Calendar.getInstance().timeInMillis
            },
            title = {
                Text(
                    text = stringResource(R.string.date_calc_section_title),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(22.dp)
                )
            }
        )

        Button(
            onClick = onCalculate,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp, vertical = 8.dp)
        ) {
            Text(text = stringResource(R.string.calculate))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DateCalcSectionPreview(){
    BookTrackerTheme {
        val state = rememberDatePickerState(
            initialDisplayMode = DisplayMode.Input
        )
        DateCalcSection(state = state, onCalculate = {})
    }
}