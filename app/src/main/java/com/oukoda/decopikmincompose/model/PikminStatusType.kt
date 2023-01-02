package com.oukoda.decopikmincompose.model

import androidx.compose.ui.graphics.Color
import com.oukoda.decopikmincompose.R
import com.oukoda.decopikmincompose.ui.theme.alreadyStatusColor
import com.oukoda.decopikmincompose.ui.theme.growingStatusColor
import com.oukoda.decopikmincompose.ui.theme.notHaveStatusColor

enum class PikminStatusType(val value: Int) {
    AlreadyExists(0),
    Growing(1),
    NotHave(2);

    companion object {
        fun create(value: Int): PikminStatusType = values().first { it.value == value }
    }

    fun update(): PikminStatusType = when (this) {
        AlreadyExists -> Growing
        Growing -> NotHave
        NotHave -> AlreadyExists
    }


    fun stringId(): Int = when (this) {
        AlreadyExists -> R.string.pikmin_status_already
        Growing -> R.string.pikmin_status_growing
        NotHave -> R.string.pikmin_status_not_have
    }

    fun stringColor(): Color = when (this) {
        AlreadyExists -> alreadyStatusColor
        Growing -> growingStatusColor
        NotHave -> notHaveStatusColor
    }
}
