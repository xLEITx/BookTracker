package com.leit.booktracker.feature_bookshelf.presentation.add_edit_book.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.leit.booktracker.R
import com.leit.booktracker.ui.theme.BookTrackerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Spinner(
    options:List<String>,
    chosenOption:String,
    label:String,
    onOptionChange:(String)-> Unit,
    modifier: Modifier = Modifier
){
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = Modifier.menuAnchor().fillMaxWidth(),
            value = chosenOption,
            onValueChange = onOptionChange,
            readOnly = true,
            label = { Text(text = label)},
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false}) {
            options.forEach{selectedOption ->
                DropdownMenuItem(
                    text = { Text(text = selectedOption)},
                    onClick = {
                        onOptionChange(selectedOption)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )

            }

        }
    }
    
    
    
}

@Preview(showBackground = true, heightDp = 320)
@Composable
fun SpinnerPreview(){
    BookTrackerTheme {
        Spinner(options = listOf("1", "2", "3"), chosenOption = "2", onOptionChange = {} , label = stringResource(R.string.choose_type))
    }
}