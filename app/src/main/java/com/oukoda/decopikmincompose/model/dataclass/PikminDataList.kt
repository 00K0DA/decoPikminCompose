package com.oukoda.decopikmincompose.model.dataclass

import com.oukoda.decopikmincompose.model.enumclass.CostumeType
import com.oukoda.decopikmincompose.model.enumclass.PikminStatusType

class PikminDataList(
    val costumeType: CostumeType,
    private var pikminDataList: List<PikminData>
) : List<PikminData> {
    fun getHaveCount(): Int =
        pikminDataList.count { it.pikminStatusType != PikminStatusType.NotHave }

    fun updatePikminData(pikminData: PikminData): PikminDataList {
        return PikminDataList(costumeType, pikminDataList.map {
            if (it.isSamePikmin(pikminData)) {
                pikminData
            } else {
                it
            }
        })
    }

    override val size: Int = pikminDataList.size

    override fun contains(element: PikminData): Boolean {
        return pikminDataList.contains(element)
    }

    override fun containsAll(elements: Collection<PikminData>): Boolean {
        return pikminDataList.containsAll(elements)
    }

    override fun get(index: Int): PikminData {
        return pikminDataList[index]
    }

    override fun indexOf(element: PikminData): Int {
        return pikminDataList.indexOf(element)
    }

    override fun isEmpty(): Boolean {
        return pikminDataList.isEmpty()
    }

    override fun iterator(): Iterator<PikminData> {
        return pikminDataList.iterator()
    }

    override fun lastIndexOf(element: PikminData): Int {
        return pikminDataList.lastIndexOf(element)
    }

    override fun listIterator(): ListIterator<PikminData> {
        return pikminDataList.listIterator()
    }

    override fun listIterator(index: Int): ListIterator<PikminData> {
        return listIterator(index)
    }

    override fun subList(fromIndex: Int, toIndex: Int): List<PikminData> {
        return pikminDataList.subList(fromIndex, toIndex)
    }

    override fun equals(other: Any?): Boolean {
        if (other is PikminDataList) {
            return this.pikminDataList == other.pikminDataList && this.costumeType == other.costumeType
        }
        return false
    }

    override fun hashCode(): Int {
        var result = costumeType.hashCode()
        result = 31 * result + pikminDataList.hashCode()
        result = 31 * result + size
        return result
    }
}