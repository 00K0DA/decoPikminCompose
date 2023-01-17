package com.oukoda.decopikmincompose.model.enumclass

import androidx.compose.ui.graphics.Color
import com.oukoda.decopikmincompose.R
import com.oukoda.decopikmincompose.ui.theme.bluePikminColor
import com.oukoda.decopikmincompose.ui.theme.purplePikminColor
import com.oukoda.decopikmincompose.ui.theme.redPikminColor
import com.oukoda.decopikmincompose.ui.theme.rockPikminColor
import com.oukoda.decopikmincompose.ui.theme.whitePikminColor
import com.oukoda.decopikmincompose.ui.theme.wingPikminColor
import com.oukoda.decopikmincompose.ui.theme.yellowPikminColor

enum class PikminType() {
    Red,
    Blue,
    Yellow,
    White,
    Purple,
    Rock,
    Wing,
    ;

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
