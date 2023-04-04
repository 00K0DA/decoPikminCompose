package com.oukoda.decopikmincompose.model.enumclass

import androidx.compose.ui.graphics.Color
import org.junit.Assert.assertEquals
import org.junit.Test

class PikminStatusTypeTest {
    @Test
    fun create() {
        var pikminStatusType = PikminStatusType.create(PikminStatusType.AlreadyExists.value)
        assertEquals(PikminStatusType.AlreadyExists, pikminStatusType)
        pikminStatusType = PikminStatusType.create(PikminStatusType.Growing.value)
        assertEquals(PikminStatusType.Growing, pikminStatusType)
        pikminStatusType = PikminStatusType.create(PikminStatusType.NotHave.value)
        assertEquals(PikminStatusType.NotHave, pikminStatusType)
    }

    @Test
    fun update() {
        var pikminStatusType = PikminStatusType.create(PikminStatusType.AlreadyExists.value)
        pikminStatusType = pikminStatusType.update()
        assertEquals(PikminStatusType.Growing, pikminStatusType)
        pikminStatusType = pikminStatusType.update()
        assertEquals(PikminStatusType.NotHave, pikminStatusType)
        pikminStatusType = pikminStatusType.update()
        assertEquals(PikminStatusType.AlreadyExists, pikminStatusType)
    }

    @Test
    fun isStringIdUnique() {
        val stringIds: List<Int> = PikminStatusType.values().map {
            it.stringId
        }

        stringIds.forEach { stringId ->
            val stringIdCount = stringIds.count { it == stringId }
            assertEquals(1, stringIdCount)
        }
    }

    @Test
    fun isStringColorUnique() {
        val stringColors: List<Color> = PikminStatusType.values().map {
            it.stringColor()
        }

        stringColors.forEach { color ->
            val stringColorCount = stringColors.count { it == color }
            assertEquals(1, stringColorCount)
        }
    }
}
