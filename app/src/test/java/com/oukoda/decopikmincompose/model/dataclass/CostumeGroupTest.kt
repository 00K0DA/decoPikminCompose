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

class CostumeGroupTest {

    private lateinit var pikminIdentifiersBase: MutableList<PikminIdentifier>

    @Before
    fun setUp() {
        pikminIdentifiersBase = mutableListOf(
            PikminIdentifier(PikminType.Red, 0, PikminStatusType.NotHave),
            PikminIdentifier(PikminType.Blue, 0, PikminStatusType.NotHave),
            PikminIdentifier(PikminType.Yellow, 0, PikminStatusType.NotHave),
            PikminIdentifier(PikminType.White, 0, PikminStatusType.NotHave),
            PikminIdentifier(PikminType.Purple, 0, PikminStatusType.NotHave),
            PikminIdentifier(PikminType.Wing, 0, PikminStatusType.NotHave),
            PikminIdentifier(PikminType.Rock, 0, PikminStatusType.NotHave),
        )
    }

    @Test
    fun size1() {
        val costumeGroup = CostumeGroup(
            CostumeType.Acorn,
            listOf(PikminIdentifier(PikminType.Red, 0, PikminStatusType.NotHave)),
        )
        assertEquals(1, costumeGroup.size)
    }

    @Test
    fun size7() {
        val costumeGroup = CostumeGroup(
            CostumeType.Acorn,
            pikminIdentifiersBase,
        )
        assertEquals(7, costumeGroup.size)
    }

    @Test
    fun getHaveCountAllNotHave() {
        val costumeGroup = CostumeGroup(
            CostumeType.Acorn,
            pikminIdentifiersBase.map {
                it.copy(pikminStatusType = PikminStatusType.NotHave)
            },
        )
        assertEquals(0, costumeGroup.getHaveCount())
    }

    @Test
    fun getHaveCountAllAlready() {
        val costumeGroup = CostumeGroup(
            CostumeType.Acorn,
            pikminIdentifiersBase.map {
                it.copy(pikminStatusType = PikminStatusType.AlreadyExists)
            },
        )
        assertEquals(costumeGroup.size, costumeGroup.getHaveCount())
    }

    @Test
    fun getHaveCountAllGrowing() {
        val costumeGroup = CostumeGroup(
            CostumeType.Acorn,
            pikminIdentifiersBase.map {
                it.copy(pikminStatusType = PikminStatusType.Growing)
            },
        )
        assertEquals(costumeGroup.size, costumeGroup.getHaveCount())
    }

    @Test
    fun updatePikminData() {
        val newPikminIdentifier =
            PikminIdentifier(PikminType.Red, 0, PikminStatusType.AlreadyExists)
        val oldCostumeGroup = CostumeGroup(
            CostumeType.Acorn,
            pikminIdentifiersBase,
        )
        val newCostumeGroup = oldCostumeGroup.updatePikminData(newPikminIdentifier)
        assertTrue(newCostumeGroup.contains(newPikminIdentifier))

        for (index in newCostumeGroup.indices) {
            if (newCostumeGroup[index].pikminType == newPikminIdentifier.pikminType) {
                assertTrue(newCostumeGroup[index] == newPikminIdentifier)
                assertEquals(
                    PikminStatusType.AlreadyExists,
                    newCostumeGroup[index].pikminStatusType,
                )
            } else {
                assertTrue(newCostumeGroup[index] == oldCostumeGroup[index])
                assertEquals(
                    PikminStatusType.NotHave,
                    newCostumeGroup[index].pikminStatusType,
                )
            }
        }
    }

    @Test
    fun applyPikminRecords() {
        val oldCostumeGroup = CostumeGroup(
            CostumeType.Acorn,
            pikminIdentifiersBase,
        )

        val prTemplate = PikminRecord(
            DecorType.Airport,
            CostumeType.Acorn,
            PikminType.Red,
            0,
            PikminStatusType.AlreadyExists,
        )

        val pikminRecords: List<PikminRecord> = PikminType.values().map {
            prTemplate.copy(pikminType = it, pikminStatus = PikminStatusType.AlreadyExists)
        }

        val newCostumeGroup = oldCostumeGroup.updateByPikminRecords(pikminRecords)

        newCostumeGroup.forEach {
            assertEquals(PikminStatusType.AlreadyExists, it.pikminStatusType)
        }
    }

    @Test
    fun compareToPikminRecordsNoInsertDelete() {
        val costumeGroup = CostumeGroup(
            CostumeType.Acorn,
            pikminIdentifiersBase,
        )
        val prTemplate = PikminRecord(
            DecorType.Airport,
            CostumeType.Acorn,
            PikminType.Red,
            0,
            PikminStatusType.AlreadyExists,
        )

        val pikminRecords: List<PikminRecord> = PikminType.values().map {
            prTemplate.copy(pikminType = it)
        }

        val (insertList, deleteList) = costumeGroup.compareToPikminRecords(
            DecorType.Airport,
            pikminRecords,
        )
        assertEquals(0, insertList.size)
        assertEquals(0, deleteList.size)
    }

    @Test
    fun compareToPikminRecordsNoInsert() {
        val costumeGroup = CostumeGroup(
            CostumeType.Acorn,
            pikminIdentifiersBase,
        )
        val prTemplate = PikminRecord(
            DecorType.Airport,
            CostumeType.Acorn,
            PikminType.Red,
            0,
            PikminStatusType.AlreadyExists,
        )

        val pikminRecords: MutableList<PikminRecord> = PikminType.values().map {
            prTemplate.copy(pikminType = it)
        } as MutableList<PikminRecord>

        val deletePikminRecord = prTemplate.copy(pikminNumber = 1)
        pikminRecords.add(deletePikminRecord)

        val (insertList, deleteList) = costumeGroup.compareToPikminRecords(
            DecorType.Airport,
            pikminRecords,
        )

        assertEquals(0, insertList.size)
        assertEquals(1, deleteList.size)
        assertEquals(deletePikminRecord, deleteList.first())
    }

    @Test
    fun compareToPikminRecordsNoDelete() {
        val costumeGroup = CostumeGroup(
            CostumeType.Acorn,
            pikminIdentifiersBase,
        )
        val prTemplate = PikminRecord(
            DecorType.Airport,
            CostumeType.Acorn,
            PikminType.Red,
            0,
            PikminStatusType.AlreadyExists,
        )

        val pikminRecords: MutableList<PikminRecord> = PikminType.values().map {
            prTemplate.copy(pikminType = it)
        } as MutableList<PikminRecord>

        val insertRecord = pikminRecords[0]
        pikminRecords.removeAt(0)

        val (insertList, deleteList) = costumeGroup.compareToPikminRecords(
            DecorType.Airport,
            pikminRecords,
        )
        assertEquals(1, insertList.size)
        assertEquals(0, deleteList.size)
        assertEquals(
            insertRecord.copy(pikminStatus = PikminStatusType.NotHave),
            insertList[0],
        )
    }

    @Test
    fun compareToPikminRecordsAllInsert() {
        val costumeGroup = CostumeGroup(
            CostumeType.Acorn,
            pikminIdentifiersBase,
        )

        val pikminRecords: MutableList<PikminRecord> = mutableListOf()

        val (insertList, deleteList) = costumeGroup.compareToPikminRecords(
            DecorType.Airport,
            pikminRecords,
        )
        assertEquals(costumeGroup.size, insertList.size)
        assertEquals(0, deleteList.size)
    }

    @Test
    fun isCompletedTrue() {
        val costumeGroup7 = CostumeGroup(
            CostumeType.Acorn,
            CostumeType.Acorn.pikminTypes().map {
                PikminIdentifier(it, 0, PikminStatusType.AlreadyExists)
            },
        )
        assertTrue(costumeGroup7.isCompleted())
    }

    @Test
    fun isCompletedFalse() {
        val costumeGroup7 = CostumeGroup(
            CostumeType.Acorn,
            CostumeType.Acorn.pikminTypes().map {
                PikminIdentifier(it, 0, PikminStatusType.AlreadyExists)
            },
        )
        costumeGroup7.updatePikminData(
            PikminIdentifier(
                PikminType.Red,
                0,
                PikminStatusType.NotHave,
            ),
        )
        assertFalse(costumeGroup7.isCompleted())
    }
}
