package com.oukoda.decopikmincompose.model.dataclass

import com.oukoda.decopikmincompose.model.enumclass.DecorType

class DecorGroup(
    val decorType: DecorType,
    private val costumeGroups: List<CostumeGroup>,
) : List<CostumeGroup> {
    override val size: Int = costumeGroups.size

    fun updateCostumeGroup(costumeGroup: CostumeGroup): DecorGroup {
        return DecorGroup(
            decorType,
            costumeGroups.map {
                if (it.costumeType == costumeGroup.costumeType) {
                    costumeGroup
                } else {
                    it
                }
            },
        )
    }

    fun isCompleted(): Boolean {
        costumeGroups.forEach { costumeGroup ->
            if (!costumeGroup.isCompleted()) {
                return false
            }
        }
        return true
    }

    fun getCount() = costumeGroups.sumOf { it.count() }

    fun getHaveCount() = costumeGroups.sumOf { it.getHaveCount() }

    override fun contains(element: CostumeGroup): Boolean {
        return costumeGroups.contains(element)
    }

    override fun containsAll(elements: Collection<CostumeGroup>): Boolean {
        return costumeGroups.containsAll(elements)
    }

    override fun get(index: Int): CostumeGroup {
        return costumeGroups[index]
    }

    override fun indexOf(element: CostumeGroup): Int {
        return costumeGroups.indexOf(element)
    }

    override fun isEmpty(): Boolean {
        return costumeGroups.isEmpty()
    }

    override fun iterator(): Iterator<CostumeGroup> {
        return costumeGroups.iterator()
    }

    override fun lastIndexOf(element: CostumeGroup): Int {
        return costumeGroups.lastIndexOf(element)
    }

    override fun listIterator(): ListIterator<CostumeGroup> {
        return costumeGroups.listIterator()
    }

    override fun listIterator(index: Int): ListIterator<CostumeGroup> {
        return costumeGroups.listIterator(index)
    }

    override fun subList(fromIndex: Int, toIndex: Int): List<CostumeGroup> {
        return costumeGroups.subList(fromIndex, toIndex)
    }
}
