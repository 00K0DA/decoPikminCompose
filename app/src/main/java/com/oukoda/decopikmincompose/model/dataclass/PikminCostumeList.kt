package com.oukoda.decopikmincompose.model.dataclass

import com.oukoda.decopikmincompose.model.enumclass.DecorType
import com.oukoda.decopikmincompose.model.enumclass.PikminStatusType

class PikminCostumeList(
    val decorType: DecorType,
    private val pikminDataLists: List<PikminDataList>
) : List<PikminDataList> {
    override val size: Int = pikminDataLists.size

    fun updatePikminDataList(pikminDataList: PikminDataList): PikminCostumeList {
        return PikminCostumeList(decorType, pikminDataLists.map {
            if (it.costumeType == pikminDataList.costumeType) {
                pikminDataList
            } else {
                it
            }
        })
    }

    fun isCompleted(): Boolean {
        pikminDataLists.forEach { pikminDataList ->
            pikminDataList.forEach {
                if (it.pikminStatusType == PikminStatusType.NotHave) {
                    return false
                }
            }
        }
        return true
    }

    override fun contains(element: PikminDataList): Boolean {
        return pikminDataLists.contains(element)
    }

    override fun containsAll(elements: Collection<PikminDataList>): Boolean {
        return pikminDataLists.containsAll(elements)
    }

    override fun get(index: Int): PikminDataList {
        return pikminDataLists[index]
    }

    override fun indexOf(element: PikminDataList): Int {
        return pikminDataLists.indexOf(element)
    }

    override fun isEmpty(): Boolean {
        return pikminDataLists.isEmpty()
    }

    override fun iterator(): Iterator<PikminDataList> {
        return pikminDataLists.iterator()
    }

    override fun lastIndexOf(element: PikminDataList): Int {
        return pikminDataLists.lastIndexOf(element)
    }

    override fun listIterator(): ListIterator<PikminDataList> {
        return pikminDataLists.listIterator()
    }

    override fun listIterator(index: Int): ListIterator<PikminDataList> {
        return pikminDataLists.listIterator(index)
    }

    override fun subList(fromIndex: Int, toIndex: Int): List<PikminDataList> {
        return pikminDataLists.subList(fromIndex, toIndex)
    }
}