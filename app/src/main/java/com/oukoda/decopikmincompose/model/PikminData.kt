package com.oukoda.decopikmincompose.model

data class PikminData(
    val decorType: DecorType,
    val costumeType: CostumeType,
    val pikminType: PikminType,
    val number: Int,
    var pikminStatusType: PikminStatusType
) {
    companion object {
        fun newInstance(
            decorType: DecorType,
            costumeType: CostumeType,
            pikminType: PikminType,
            number: Int
        ): PikminData {
            return PikminData(decorType, costumeType, pikminType, number, PikminStatusType.NotHave)
        }
    }

    fun createStatusKey(): String {
        return "${pikminType.value}-${costumeType.value}-${number}"
    }

    fun statusUpdate() {
        pikminStatusType = pikminStatusType.update()
    }

    override fun equals(other: Any?): Boolean {
        return if (other is PikminData) {
            this.decorType == other.decorType &&
                    this.costumeType == other.costumeType &&
                    this.pikminType == other.pikminType &&
                    this.number == other.number
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        var result = decorType.hashCode()
        result = 31 * result + costumeType.hashCode()
        result = 31 * result + pikminType.hashCode()
        result = 31 * result + number
        return result
    }
}