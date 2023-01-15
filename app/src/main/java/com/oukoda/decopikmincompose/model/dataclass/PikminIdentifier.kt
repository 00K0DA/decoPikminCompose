package com.oukoda.decopikmincompose.model.dataclass

import com.oukoda.decopikmincompose.model.enumclass.CostumeType
import com.oukoda.decopikmincompose.model.enumclass.DecorType
import com.oukoda.decopikmincompose.model.enumclass.PikminStatusType
import com.oukoda.decopikmincompose.model.enumclass.PikminType
import com.oukoda.decopikmincompose.model.room.entity.PikminRecord

data class PikminIdentifier(
    val pikminType: PikminType,
    val number: Int,
    val pikminStatusType: PikminStatusType,
) {
    companion object {
        fun newInstance(
            pikminType: PikminType,
            number: Int,
        ): PikminIdentifier {
            return PikminIdentifier(pikminType, number, PikminStatusType.NotHave)
        }
    }

    fun statusUpdate(): PikminIdentifier {
        return this.copy(pikminStatusType = pikminStatusType.update())
    }

    fun isSamePikmin(pikminIdentifier: PikminIdentifier): Boolean {
        return this.pikminType == pikminIdentifier.pikminType &&
            this.number == pikminIdentifier.number
    }

    fun isSamePikmin(record: PikminRecord): Boolean {
        if (record.pikminType != pikminType) return false
        if (record.pikminNumber != number) return false
        return true
    }

    fun applyRecord(record: PikminRecord): PikminIdentifier {
        return PikminIdentifier(pikminType, number, record.pikminStatus)
    }

    fun toPikminRecord(decorType: DecorType, costumeType: CostumeType): PikminRecord {
        return PikminRecord(decorType, costumeType, pikminType, number, pikminStatusType)
    }

    override fun hashCode(): Int {
        var result = 31 * pikminType.hashCode()
        result = 31 * result + number
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PikminIdentifier

        if (pikminType != other.pikminType) return false
        if (number != other.number) return false
        if (pikminStatusType != other.pikminStatusType) return false

        return true
    }
}
