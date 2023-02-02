package com.oukoda.decopikmincompose.model.dataclass

import com.oukoda.decopikmincompose.model.enumclass.CostumeType
import com.oukoda.decopikmincompose.model.enumclass.DecorType
import com.oukoda.decopikmincompose.model.enumclass.PikminStatusType
import com.oukoda.decopikmincompose.model.enumclass.PikminType
import com.oukoda.decopikmincompose.model.room.entity.PikminRecord
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DecorGroupTest {

    private val decorType = DecorType.Forest
    private val costumeType1 = CostumeType.Acorn
    private val costumeType2 = CostumeType.StagBeetle
    private lateinit var costumeGroup1: CostumeGroup
    private lateinit var costumeGroup2: CostumeGroup

    @Before
    fun setUp() {
        costumeGroup1 = CostumeGroup.newInstance(costumeType1)
        costumeGroup2 = CostumeGroup.newInstance(costumeType2)
    }

    @Test
    fun isCompletedTrue() {
        val costumeGroup1 = CostumeGroup(
            costumeType1,
            costumeGroup1.map {
                it.copy(pikminStatusType = PikminStatusType.AlreadyExists)
            },
        )

        val costumeGroup2 = CostumeGroup(
            costumeType2,
            costumeGroup2.map {
                it.copy(pikminStatusType = PikminStatusType.AlreadyExists)
            },
        )

        var decorGroup = DecorGroup(decorType, listOf(costumeGroup1))
        assertTrue(decorGroup.isCompleted())

        decorGroup = DecorGroup(decorType, listOf(costumeGroup1, costumeGroup2))
        assertTrue(decorGroup.isCompleted())
    }

    @Test
    fun isCompletedFalse() {
        // すべてNotHave
        val decorGroup = DecorGroup(decorType, listOf(costumeGroup1, costumeGroup2))
        assertFalse(decorGroup.isCompleted())

        // 一つだけAlreadyExistsになっている場合
        costumeGroup1 = costumeGroup1.updateByPikminRecords(
            listOf(
                PikminRecord(
                    decorType,
                    costumeType1,
                    PikminType.Red,
                    0,
                    PikminStatusType.AlreadyExists,
                ),
            ),
        )
        decorGroup.updateCostumeGroup(costumeGroup1)
        assertFalse(decorGroup.isCompleted())
    }

    @Test
    fun updatePikminIdentifiers() {
        var decorGroup = DecorGroup(decorType, listOf(costumeGroup1, costumeGroup2))
        assertFalse(decorGroup.isCompleted())
        val costumeGroup1 = CostumeGroup(
            costumeType1,
            costumeGroup1.map {
                it.copy(pikminStatusType = PikminStatusType.AlreadyExists)
            },
        )
        decorGroup = decorGroup.updateCostumeGroup(costumeGroup1)

        val costumeGroup2 = CostumeGroup(
            costumeType2,
            costumeGroup2.map {
                it.copy(pikminStatusType = PikminStatusType.AlreadyExists)
            },
        )
        decorGroup = decorGroup.updateCostumeGroup(costumeGroup2)
        assertTrue(decorGroup.isCompleted())
    }

    @Test
    fun getCountTest() {
        var pikminCount = costumeGroup1.count()
        var decorGroup = DecorGroup(DecorType.Forest, listOf(costumeGroup1))
        assertEquals(pikminCount, decorGroup.getCount())

        pikminCount = costumeGroup1.count() + costumeGroup2.count()
        decorGroup = DecorGroup(DecorType.Forest, listOf(costumeGroup1, costumeGroup2))
        assertEquals(pikminCount, decorGroup.getCount())
    }

    @Test
    fun getHaveCountTest() {
        var decorGroup =
            DecorGroup(decorType, listOf(costumeGroup1, costumeGroup2))
        assertEquals(0, decorGroup.getHaveCount())
        costumeGroup1 = costumeGroup1.updateByPikminRecords(
            listOf(
                PikminRecord(
                    decorType,
                    costumeType1,
                    PikminType.Red,
                    0,
                    PikminStatusType.AlreadyExists,
                ),
            ),
        )
        decorGroup = decorGroup.updateCostumeGroup(costumeGroup1)
        assertEquals(1, decorGroup.getHaveCount())

        costumeGroup2 = costumeGroup2.updateByPikminRecords(
            listOf(
                PikminRecord(
                    decorType,
                    costumeType2,
                    PikminType.Red,
                    0,
                    PikminStatusType.AlreadyExists,
                ),
            ),
        )
        decorGroup = decorGroup.updateCostumeGroup(costumeGroup2)
        assertEquals(2, decorGroup.getHaveCount())
    }
}
