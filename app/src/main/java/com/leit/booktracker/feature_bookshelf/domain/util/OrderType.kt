package com.leit.booktracker.feature_bookshelf.domain.util


/**
 * Sealed class for choosing how to sort list ( in descending or ascending order)
 */
sealed class OrderType{
    object Ascending:OrderType()
    object Descending:OrderType()
}
