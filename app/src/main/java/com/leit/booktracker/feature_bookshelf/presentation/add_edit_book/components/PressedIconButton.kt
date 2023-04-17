package com.leit.booktracker.feature_bookshelf.presentation.add_edit_book.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector



@Composable
fun PressedIconButton(
    enabled:Boolean,
    icon: ImageVector,
    iconDescription:String,
    onPress:() -> Unit,
    onDispose:() -> Unit
){
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    if (isPressed){
        onPress()
        DisposableEffect(Unit) {
            onDispose {
                onDispose()
            }
        }
    }

    IconButton(
        onClick = {

        },
        enabled = enabled,
        interactionSource = interactionSource
    ) {
        Icon(
            imageVector = icon,
            contentDescription = iconDescription
        )
    }

}