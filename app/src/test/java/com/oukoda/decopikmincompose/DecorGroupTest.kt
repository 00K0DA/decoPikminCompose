package com.oukoda.decopikmincompose

import com.oukoda.decopikmincompose.model.dataclass.CostumeGroup
import com.oukoda.decopikmincompose.model.dataclass.DecorGroup
import com.oukoda.decopikmincompose.model.dataclass.PikminIdentifier
import com.oukoda.decopikmincompose.model.enumclass.CostumeType
import com.oukoda.decopikmincompose.model.enumclass.DecorType
import com.oukoda.decopikmincompose.model.enumclass.PikminStatusType
import com.oukoda.decopikmincompose.model.enumclass.PikminType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DecorGroupTest {
    private lateinit var pikminIdentifierRed: PikminIdentifier
    private lateinit var pikminIdentifierBlue: PikminIdentifier
    private lateinit var costumeGroupAcornRed: CostumeGroup
    private lateinit var costumeGroupAcornBlue: CostumeGroup
    private lateinit var costumeGroupBaguetteRed: CostumeGroup
    private lateinit var costumeGroupBaguetteBlue: CostumeGroup

    @Before
    fun setUp() {
        pikminIdentifierRed = PikminIdentifier.newInstance(PikminType.Red, 0)
        pikminIdentifierBlue = PikminIdentifier.newInstance(PikminType.Blue, 0)
        costumeGroupAcornRed = CostumeGroup(
            CostumeType.Acorn,
            listOf(pikminIdentifierRed),
        )
        costumeGroupAcornBlue = CostumeGroup(
            CostumeType.Acorn,
            listOf(pikminIdentifierBlue),
        )

        costumeGroupBaguetteRed = CostumeGroup(
            CostumeType.Baguette,
            listOf(pikminIdentifierRed),
        )

        costumeGroupBaguetteBlue = CostumeGroup(
            CostumeType.Baguette,
            listOf(pikminIdentifierBlue),
        )
    }

    @Test
    fun updatePikminIdentifiers() {
        var decorGroup =
            DecorGroup(DecorType.Airport, listOf(costumeGroupAcornRed, costumeGroupBaguetteRed))
        decorGroup = decorGroup.updateCostumeGroup(costumeGroupAcornBlue)
        decorGroup = decorGroup.updateCostumeGroup(costumeGroupBaguetteBlue)

        decorGroup.forEach {
            if (it.costumeType == CostumeType.Acorn) {
                assertEquals(it, costumeGroupAcornBlue)
            } else if (it.costumeType == CostumeType.Baguette) {
                assertEquals(it, costumeGroupBaguetteBlue)
            }
        }
    }

    @Test
    fun isCompletedTrue() {
        val pikminIdentifiers = (0..7).map {
            pikminIdentifierRed.copy(number = it, pikminStatusType = PikminStatusType.AlreadyExists)
        }
        val costumeGroup = CostumeGroup(CostumeType.Acorn, pikminIdentifiers)
        val decorGroup =
            DecorGroup(DecorType.Airport, listOf(costumeGroup, costumeGroup, costumeGroup))
        assertTrue(decorGroup.isCompleted())
    }

    @Test
    fun isCompletedFalse() {
        val decorType = DecorType.Airport
        val costumeType = CostumeType.Acorn
        val pikminIdentifiers = (0..7).map {
            pikminIdentifierRed.copy(number = it, pikminStatusType = PikminStatusType.NotHave)
        }
        // すべてNotHave
        val costumeGroup = CostumeGroup(costumeType, pikminIdentifiers)
        val decorGroup = DecorGroup(decorType, listOf(costumeGroup, costumeGroup, costumeGroup))
        assertFalse(decorGroup.isCompleted())

        // 一つだけAlreadyExistsになっている場合
        costumeGroup.applyPikminRecords(
            listOf(
                costumeGroup.first().toPikminRecord(decorType, costumeType)
                    .copy(pikminStatus = PikminStatusType.AlreadyExists),
            ),
        )
        decorGroup.updateCostumeGroup(costumeGroup)
        assertFalse(decorGroup.isCompleted())
    }
}
