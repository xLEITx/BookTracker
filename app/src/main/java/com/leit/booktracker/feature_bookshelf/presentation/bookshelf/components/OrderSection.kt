package com.leit.booktracker.feature_bookshelf.presentation.bookshelf.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leit.booktracker.R
import com.leit.booktracker.feature_bookshelf.domain.util.BookOrder
import com.leit.booktracker.feature_bookshelf.domain.util.OrderType
import com.leit.booktracker.ui.theme.BookTrackerTheme

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    bookOrder: BookOrder = BookOrder.Status(OrderType.Descending),
    onOrderChange: (BookOrder) -> Unit
) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = stringResource(R.string.status),
                selected = bookOrder is BookOrder.Status,
                onSelect = {onOrderChange(BookOrder.Status(bookOrder.orderType))}
            )
            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = stringResource(R.string.title),
                selected = bookOrder is BookOrder.Title,
                onSelect = {onOrderChange(BookOrder.Title(bookOrder.orderType))}
            )

        }
        Row(modifier = Modifier.fillMaxWidth()){
            DefaultRadioButton(
                text = stringResource(R.string.author),
                selected = bookOrder is BookOrder.Author,
                onSelect = {onOrderChange(BookOrder.Author(bookOrder.orderType))}
            )
            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = stringResource(R.string.type),
                selected = bookOrder is BookOrder.Type,
                onSelect = {onOrderChange(BookOrder.Type(bookOrder.orderType))}
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = stringResource(R.string.descending),
                selected = bookOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(bookOrder.copy(OrderType.Descending))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = stringResource(R.string.ascending),
                selected = bookOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(bookOrder.copy(OrderType.Ascending))
                }
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun OrderSectionPreview(){
    BookTrackerTheme() {
        OrderSection(onOrderChange = {})
    }
}