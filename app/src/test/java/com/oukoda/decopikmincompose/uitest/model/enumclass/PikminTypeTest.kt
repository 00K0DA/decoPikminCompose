package com.oukoda.decopikmincompose.uitest.model.enumclass

import androidx.compose.ui.graphics.Color
import com.oukoda.decopikmincompose.model.enumclass.PikminType
import org.junit.Assert.assertEquals
import org.junit.Test

class PikminTypeTest {
    @Test
    fun isColorUnique() {
        val colors: List<Color> = PikminType.values().map {
            it.color()
        }

        colors.forEach { color ->
            val stringIdCount = colors.count { it == color }
            assertEquals(1, stringIdCount)
        }
    }

    @Test
    fun isStringIdUnique() {
        val stringIds: List<Int> = PikminType.values().map {
            it.stringId()
        }

        stringIds.forEach { stringId ->
            val stringIdCount = stringIds.count { it == stringId }
            assertEquals(1, stringIdCount)
        }
    }
}
