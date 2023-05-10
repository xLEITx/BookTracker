package com.leit.booktracker.feature_bookshelf.presentation.util

import androidx.annotation.StringRes


data class FilterOption(
    @StringRes
    val stringRes: Int,
    val isSelected: Boolean,
    val onClick: () -> Unit
)