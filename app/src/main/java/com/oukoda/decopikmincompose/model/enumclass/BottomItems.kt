package com.oukoda.decopikmincompose.model.enumclass

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.oukoda.decopikmincompose.R

enum class BottomItems() {
    Normal,
    Special,

    ;

    fun stringId(): Int {
        return when (this) {
            Normal -> R.string.bottom_item_normal
            Special -> R.string.bottom_item_special
        }
    }

    fun route(): String {
        return when (this) {
            Normal -> "normal"
            Special -> "special"
        }
    }

    fun imageVector(): ImageVector {
        return when (this) {
            Normal -> Icons.Filled.List
            Special -> Icons.Filled.Star
        }
    }

    fun index(): Int {
        return BottomItems.values().indexOf(this)
    }
}
