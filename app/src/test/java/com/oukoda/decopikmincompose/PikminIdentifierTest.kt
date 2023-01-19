package com.oukoda.decopikmincompose

import com.oukoda.decopikmincompose.model.dataclass.PikminIdentifier
import com.oukoda.decopikmincompose.model.enumclass.CostumeType
import com.oukoda.decopikmincompose.model.enumclass.DecorType
import com.oukoda.decopikmincompose.model.enumclass.PikminStatusType
import com.oukoda.decopikmincompose.model.enumclass.PikminType
import com.oukoda.decopikmincompose.model.room.entity.PikminRecord
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PikminIdentifierTest {
    @Before
    fun setUp() {
    }

    @Test
    fun statusUpdate() {
        var pikminIdentifier = PikminIdentifier.newInstance(PikminType.Red, number = 0)
        assertEquals(PikminStatusType.NotHave, pikminIdentifier.pikminStatusType)
        pikminIdentifier = pikminIdentifier.statusUpdate()
        assertEquals(PikminStatusType.AlreadyExists, pikminIdentifier.pikminStatusType)
        pikminIdentifier = pikminIdentifier.statusUpdate()
        assertEquals(PikminStatusType.Growing, pikminIdentifier.pikminStatusType)
    }

    @Test
    fun isSamePikminIdentifierTrue() {
        val pikminIdentifier1 = PikminIdentifier(PikminType.Red, 0, PikminStatusType.NotHave)
        val pikminIdentifier2 = PikminIdentifier(PikminType.Red, 0, PikminStatusType.AlreadyExists)
        assertTrue(pikminIdentifier1.isSamePikmin(pikminIdentifier2))
        assertTrue(pikminIdentifier2.isSamePikmin(pikminIdentifier1))
    }

    @Test
    fun isSamePikminIdentifierFalse() {
        val pikminIdentifier1 = PikminIdentifier(PikminType.Red, 0, PikminStatusType.NotHave)
        val pikminIdentifier2 = PikminIdentifier(PikminType.Red, 1, PikminStatusType.NotHave)
        val pikminIdentifier3 = PikminIdentifier(PikminType.Blue, 0, PikminStatusType.NotHave)
        assertFalse(pikminIdentifier1.isSamePikmin(pikminIdentifier2))
        assertFalse(pikminIdentifier1.isSamePikmin(pikminIdentifier3))
        assertFalse(pikminIdentifier2.isSamePikmin(pikminIdentifier1))
        assertFalse(pikminIdentifier2.isSamePikmin(pikminIdentifier3))
        assertFalse(pikminIdentifier3.isSamePikmin(pikminIdentifier1))
        assertFalse(pikminIdentifier3.isSamePikmin(pikminIdentifier2))
    }

    @Test
    fun isSamePikminRecordTrue() {
        val pikminIdentifier = PikminIdentifier(PikminType.Red, 0, PikminStatusType.NotHave)
        val pikminRecord1 = PikminRecord(
            DecorType.Airport,
            CostumeType.Acorn,
            PikminType.Red,
            0,
            PikminStatusType.NotHave,
        )
        val pikminRecord2 = pikminRecord1.copy(pikminStatus = PikminStatusType.AlreadyExists)
        assertTrue(pikminIdentifier.isSamePikmin(pikminRecord1))
        assertTrue(pikminIdentifier.isSamePikmin(pikminRecord2))
    }

    @Test
    fun isSamePikminRecordFalse() {
        val pikminIdentifier = PikminIdentifier(PikminType.Red, 0, PikminStatusType.NotHave)
        val pikminRecord1 = PikminRecord(
            DecorType.Airport,
            CostumeType.Acorn,
            PikminType.Blue,
            0,
            PikminStatusType.NotHave,
        )
        val pikminRecord2 =
            pikminRecord1.copy(pikminNumber = 1, pikminStatus = PikminStatusType.AlreadyExists)
        assertFalse(pikminIdentifier.isSamePikmin(pikminRecord1))
        assertFalse(pikminIdentifier.isSamePikmin(pikminRecord2))
    }

    @Test
    fun applyRecord() {
        val pikminIdentifier = PikminIdentifier(PikminType.Red, 0, PikminStatusType.NotHave)
        val alreadyRecord = PikminRecord(
            DecorType.Airport,
            CostumeType.Acorn,
            PikminType.Red,
            0,
            PikminStatusType.AlreadyExists,
        )
        val growingRecord = alreadyRecord.copy(pikminStatus = PikminStatusType.Growing)
        val notHaveRecord = growingRecord.copy(pikminStatus = PikminStatusType.NotHave)
        assertEquals(
            PikminStatusType.AlreadyExists,
            pikminIdentifier.applyRecord(alreadyRecord).pikminStatusType,
        )
        assertEquals(
            PikminStatusType.Growing,
            pikminIdentifier.applyRecord(growingRecord).pikminStatusType,
        )
        assertEquals(
            PikminStatusType.NotHave,
            pikminIdentifier.applyRecord(notHaveRecord).pikminStatusType,
        )
    }

    @Test
    fun toPikminRecord() {
        val decorType = DecorType.Airport
        val costumeType = CostumeType.Acorn
        val pikminType = PikminType.Red
        val number = 0
        val status = PikminStatusType.NotHave
        val record = PikminRecord(decorType, costumeType, pikminType, number, status)
        val differentRecord = record.copy(pikminNumber = number + 1)

        val pikminIdentifier = PikminIdentifier(pikminType, number, status)
        val recordFromPikminIdentifier = pikminIdentifier.toPikminRecord(decorType, costumeType)

        assertEquals(record, recordFromPikminIdentifier)
        assertEquals(decorType, recordFromPikminIdentifier.decorType)
        assertEquals(costumeType, recordFromPikminIdentifier.costumeType)
        assertEquals(pikminType, recordFromPikminIdentifier.pikminType)
        assertEquals(number, recordFromPikminIdentifier.pikminNumber)
        assertEquals(status, recordFromPikminIdentifier.pikminStatus)

        assertNotEquals(differentRecord, recordFromPikminIdentifier)
    }
}
