package com.oukoda.decopikmincompose.model.enumclass

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.oukoda.decopikmincompose.R
import com.oukoda.decopikmincompose.ui.theme.alreadyStatusColor
import com.oukoda.decopikmincompose.ui.theme.growingStatusColor
import com.oukoda.decopikmincompose.ui.theme.notHaveStatusColor

enum class PikminStatusType(val value: Int, @StringRes val stringId: Int) {
    AlreadyExists(0, R.string.pikmin_status_already),
    Growing(1, R.string.pikmin_status_growing),
    NotHave(2, R.string.pikmin_status_not_have),
    ;

    companion object {
        fun create(value: Int): PikminStatusType = values().first { it.value == value }
    }

    fun update(): PikminStatusType = when (this) {
        AlreadyExists -> Growing
        Growing -> NotHave
        NotHave -> AlreadyExists
    }

    fun stringColor(): Color = when (this) {
        AlreadyExists -> alreadyStatusColor
        Growing -> growingStatusColor
        NotHave -> notHaveStatusColor
    }
}
