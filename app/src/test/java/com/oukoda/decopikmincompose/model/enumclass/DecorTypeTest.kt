package com.oukoda.decopikmincompose.model.enumclass

import org.junit.Assert.assertEquals
import org.junit.Test

class DecorTypeTest {
    @Test
    fun isTextIdUnique() {
        val stringIds: List<Int> = DecorType.values().map {
            it.stringId()
        }

        stringIds.forEach { stringId ->
            val stringIdCount = stringIds.count { it == stringId }
            assertEquals(1, stringIdCount)
        }
    }

    @Test
    fun isCostumeUnique() {
        val allCostumes: List<CostumeType> = DecorType.values().map {
            it.costumeTypes()
        }.flatten()
        allCostumes.forEach { costumeType ->
            val costumeCount = allCostumes.count { it == costumeType }
            assertEquals(1, costumeCount)
        }
    }
}
