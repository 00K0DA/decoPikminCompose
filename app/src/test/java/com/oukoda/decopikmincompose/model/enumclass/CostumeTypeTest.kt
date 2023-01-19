package com.oukoda.decopikmincompose.model.enumclass

import org.junit.Assert.assertEquals
import org.junit.Test

class CostumeTypeTest {
    @Test
    fun isTextIdUnique() {
        val stringIds: List<Int> = CostumeType.values().map {
            it.stringId()
        }

        stringIds.forEach { stringId ->
            val stringIdCount = stringIds.count { it == stringId }
            assertEquals(1, stringIdCount)
        }
    }
}
