package com.leit.booktracker.feature_bookshelf.presentation.util

sealed class Screen(val route:String){
    object BookShelfScreen:Screen("bookshelf_screen")
    object BookAddEditScreen:Screen("book_add_edit_screen")
    object BookDetailScreen:Screen("book_detail_screen")
}
