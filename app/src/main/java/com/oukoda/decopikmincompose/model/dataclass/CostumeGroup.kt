package com.oukoda.decopikmincompose.model.dataclass

import com.oukoda.decopikmincompose.model.enumclass.CostumeType
import com.oukoda.decopikmincompose.model.enumclass.PikminStatusType

class CostumeGroup(
    val costumeType: CostumeType,
    private var pikminIdentifiers: List<PikminIdentifier>,
) : List<PikminIdentifier> {
    fun getHaveCount(): Int =
        pikminIdentifiers.count { it.pikminStatusType != PikminStatusType.NotHave }

    fun updatePikminData(pikminIdentifier: PikminIdentifier): CostumeGroup {
        return CostumeGroup(
            costumeType,
            pikminIdentifiers.map {
                if (it.isSamePikmin(pikminIdentifier)) {
                    pikminIdentifier
                } else {
                    it
                }
            },
        )
    }

    override val size: Int = pikminIdentifiers.size

    override fun contains(element: PikminIdentifier): Boolean {
        return pikminIdentifiers.contains(element)
    }

    override fun containsAll(elements: Collection<PikminIdentifier>): Boolean {
        return pikminIdentifiers.containsAll(elements)
    }

    override fun get(index: Int): PikminIdentifier {
        return pikminIdentifiers[index]
    }

    override fun indexOf(element: PikminIdentifier): Int {
        return pikminIdentifiers.indexOf(element)
    }

    override fun isEmpty(): Boolean {
        return pikminIdentifiers.isEmpty()
    }

    override fun iterator(): Iterator<PikminIdentifier> {
        return pikminIdentifiers.iterator()
    }

    override fun lastIndexOf(element: PikminIdentifier): Int {
        return pikminIdentifiers.lastIndexOf(element)
    }

    override fun listIterator(): ListIterator<PikminIdentifier> {
        return pikminIdentifiers.listIterator()
    }

    override fun listIterator(index: Int): ListIterator<PikminIdentifier> {
        return listIterator(index)
    }

    override fun subList(fromIndex: Int, toIndex: Int): List<PikminIdentifier> {
        return pikminIdentifiers.subList(fromIndex, toIndex)
    }

    override fun equals(other: Any?): Boolean {
        if (other is CostumeGroup) {
            return this.pikminIdentifiers == other.pikminIdentifiers && this.costumeType == other.costumeType
        }
        return false
    }

    override fun hashCode(): Int {
        var result = costumeType.hashCode()
        result = 31 * result + pikminIdentifiers.hashCode()
        result = 31 * result + size
        return result
    }
}
