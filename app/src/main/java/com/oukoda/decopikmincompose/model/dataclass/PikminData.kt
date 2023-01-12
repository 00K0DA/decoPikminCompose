package com.oukoda.decopikmincompose.model.dataclass

import com.oukoda.decopikmincompose.model.enumclass.PikminStatusType
import com.oukoda.decopikmincompose.model.enumclass.PikminType

data class PikminData(
    val pikminType: PikminType,
    val number: Int,
    val pikminStatusType: PikminStatusType,
) {
    companion object {
        fun newInstance(
            pikminType: PikminType,
            number: Int,
        ): PikminData {
            return PikminData(pikminType, number, PikminStatusType.NotHave)
        }
    }

    fun statusUpdate(): PikminData {
        return this.copy(pikminStatusType = pikminStatusType.update())
    }

    fun isSamePikmin(pikminData: PikminData): Boolean {
        return this.pikminType == pikminData.pikminType &&
            this.number == pikminData.number
    }

    override fun hashCode(): Int {
        var result = 31 * pikminType.hashCode()
        result = 31 * result + number
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PikminData

        if (pikminType != other.pikminType) return false
        if (number != other.number) return false
        if (pikminStatusType != other.pikminStatusType) return false

        return true
    }
}
