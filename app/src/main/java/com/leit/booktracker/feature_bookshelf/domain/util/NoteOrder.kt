package com.leit.booktracker.feature_bookshelf.domain.util

sealed class NoteOrder{
    object Title : NoteOrder()
    object Date : NoteOrder()
}
