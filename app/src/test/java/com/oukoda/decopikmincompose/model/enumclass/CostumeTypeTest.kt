package com.oukoda.decopikmincompose.model.enumclass

import org.junit.Assert.assertEquals
import org.junit.Test

class CostumeTypeTest {
    @Test
    fun isTextIdUnique() {
        val stringIdList: List<Int> = CostumeType.values().map {
            it.getCostumeTextId()
        }

        stringIdList.forEach { stringId ->
            val stringIdCount = stringIdList.count { it == stringId }
            assertEquals(1, stringIdCount)
        }
    }
}
