package com.oukoda.decopikmincompose.model

data class PikminData(
    val decorType: DecorType,
    val pikminType: PikminType,
    val number: Int,
    val pikminStatusType: PikminStatusType
) {
    companion object {
        fun newInstance(
            decorType: DecorType,
            pikminType: PikminType,
            number: Int
        ): PikminData {
            return PikminData(decorType, pikminType, number, PikminStatusType.NotHave)
        }
    }

    fun statusUpdate(): PikminData {
        return this.copy(pikminStatusType = pikminStatusType.update())
    }

    fun isSamePikmin(pikminData: PikminData): Boolean {
        return this.decorType == pikminData.decorType &&
                this.pikminType == pikminData.pikminType &&
                this.number == pikminData.number
    }

    override fun hashCode(): Int {
        var result = decorType.hashCode()
        result = 31 * result + pikminType.hashCode()
        result = 31 * result + number
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PikminData

        if (decorType != other.decorType) return false
        if (pikminType != other.pikminType) return false
        if (number != other.number) return false
        if (pikminStatusType != other.pikminStatusType) return false

        return true
    }
}