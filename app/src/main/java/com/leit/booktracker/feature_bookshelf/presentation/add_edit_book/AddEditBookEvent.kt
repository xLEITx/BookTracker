package com.leit.booktracker.feature_bookshelf.presentation.add_edit_book

import androidx.compose.ui.focus.FocusState

sealed class AddEditBookEvent{
    data class EnteredTitle(val value:String):AddEditBookEvent()
    data class ChangeTitleFocus(val focusState: FocusState):AddEditBookEvent()
    data class EnteredAuthor(val value:String):AddEditBookEvent()
    data class ChangeAuthorFocus(val focusState: FocusState):AddEditBookEvent()
    data class SelectedType(val value:String):AddEditBookEvent()
    data class SelectedStatus(val value:String):AddEditBookEvent()
    data class ChangePages(val value:Int):AddEditBookEvent()
    object SaveBook:AddEditBookEvent()

}