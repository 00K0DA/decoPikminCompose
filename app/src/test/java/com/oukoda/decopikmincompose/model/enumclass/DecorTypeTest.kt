package com.oukoda.decopikmincompose.model.enumclass

import org.junit.Assert.assertEquals
import org.junit.Test

class DecorTypeTest {
    @Test
    fun isTextIdUnique() {
        val stringIdList: List<Int> = DecorType.values().map {
            it.getDecorText()
        }

        stringIdList.forEach { stringId ->
            val stringIdCount = stringIdList.count { it == stringId }
            assertEquals(1, stringIdCount)
        }
    }

    @Test
    fun isCostumeUnique(){
        val allCostumeList: List<CostumeType> = DecorType.values().map {
            it.getCostumes()
        }.flatten()
        allCostumeList.forEach { costumeType ->
            val costumeCount = allCostumeList.count { it == costumeType }
            assertEquals(1, costumeCount)
        }
    }
}