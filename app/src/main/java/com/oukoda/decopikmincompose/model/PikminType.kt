package com.oukoda.decopikmincompose.model

import androidx.compose.ui.graphics.Color
import com.oukoda.decopikmincompose.R
import com.oukoda.decopikmincompose.ui.theme.*

enum class PikminType(val value: String) {
    Red("Red"),
    Blue("Blue"),
    Yellow("Yellow"),
    White("White"),
    Purple("Purple"),
    Rock("Rock"),
    Wing("Wing");

    fun color(): Color = when (this) {
        Red -> redPikminColor
        Blue -> bluePikminColor
        Yellow -> yellowPikminColor
        White -> whitePikminColor
        Purple -> purplePikminColor
        Rock -> rockPikminColor
        Wing -> wingPikminColor
    }

    fun stringId(): Int = when (this) {
        Red -> R.string.pikmin_type_red
        Blue -> R.string.pikmin_type_blue
        Yellow -> R.string.pikmin_type_yellow
        White -> R.string.pikmin_type_white
        Purple -> R.string.pikmin_type_purple
        Rock -> R.string.pikmin_type_rock
        Wing -> R.string.pikmin_type_wing
    }
}
