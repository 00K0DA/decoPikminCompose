package com.oukoda.decopikmincompose.model.dataclass

import com.oukoda.decopikmincompose.model.enumclass.CostumeType
import com.oukoda.decopikmincompose.model.enumclass.DecorType
import com.oukoda.decopikmincompose.model.enumclass.PikminStatusType
import com.oukoda.decopikmincompose.model.enumclass.PikminType
import com.oukoda.decopikmincompose.model.room.entity.PikminRecord

class CostumeGroup(
    val costumeType: CostumeType,
    private val pikminIdentifiers: List<PikminIdentifier>,
) : List<PikminIdentifier> {
    companion object {
        fun newInstance(costumeType: CostumeType): CostumeGroup {
            val pikminCountByColor = PikminType.values().associateWith { 0 }.toMutableMap()
            val pikminIdentifiers = costumeType.getPikminList().map { pikminType ->
                val number = pikminCountByColor[pikminType]!!
                pikminCountByColor[pikminType] = number + 1
                PikminIdentifier.newInstance(pikminType, number)
            }
            return CostumeGroup(costumeType, pikminIdentifiers)
        }
    }

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
    fun compareToPikminRecords(
        decorType: DecorType,
        pikminRecords: List<PikminRecord>,
    ): Pair<List<PikminRecord>, List<PikminRecord>> {
        val insertRecords: MutableList<PikminRecord> = pikminIdentifiers.map {
            it.toPikminRecord(decorType, costumeType)
        } as MutableList<PikminRecord>
        val deleteRecords: MutableList<PikminRecord> = mutableListOf()

        for (pikminRecord in pikminRecords) {
            var isRecordIn = false
            for (insertRecord in insertRecords) {
                if (pikminRecord.isSamePikminRecord(insertRecord)) {
                    isRecordIn = true
                }
            }
            if (!isRecordIn) {
                deleteRecords.add(pikminRecord)
            }
        }

        val removeFromInsertRecords: MutableList<PikminRecord> = mutableListOf()
        for (record in insertRecords) {
            for (existRecord in pikminRecords) {
                if (record.isSamePikminRecord(existRecord)) {
                    removeFromInsertRecords.add(record)
                    break
                }
            }
        }

        insertRecords.removeAll(removeFromInsertRecords)
        return Pair(insertRecords.toList(), deleteRecords.toList())
    }

    fun applyPikminRecords(pikminRecords: List<PikminRecord>): CostumeGroup {
        val mutablePikminIdentifiers = pikminIdentifiers.toMutableList()
        pikminRecords.forEach { record ->
            for (index in mutablePikminIdentifiers.indices) {
                if (mutablePikminIdentifiers[index].isSamePikmin(record)) {
                    mutablePikminIdentifiers[index] =
                        mutablePikminIdentifiers[index].applyRecord(record)
                }
            }
        }
        return CostumeGroup(costumeType, mutablePikminIdentifiers.toList())
    }

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
