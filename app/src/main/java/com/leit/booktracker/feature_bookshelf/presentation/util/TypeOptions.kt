package com.leit.booktracker.feature_bookshelf.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringArrayResource
import com.leit.booktracker.R

@Composable
fun typeOptions(): Array<String>{
    return stringArrayResource(id = R.array.book_types)
}