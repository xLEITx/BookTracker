package com.leit.booktracker.feature_bookshelf.domain.use_case

data class BookShelfUseCases(
    val getBooks: GetBooks,
    val getSingleBook: GetSingleBook,
    val insertBook: InsertBook,
    val deleteBook: DeleteBook
)
