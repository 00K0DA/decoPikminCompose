package com.oukoda.decopikmincompose.model.room.entity

import androidx.room.Entity
import com.oukoda.decopikmincompose.model.enumclass.CostumeType
import com.oukoda.decopikmincompose.model.enumclass.DecorType
import com.oukoda.decopikmincompose.model.enumclass.PikminStatusType
import com.oukoda.decopikmincompose.model.enumclass.PikminType

@Entity(primaryKeys = ["decorType", "costumeType", "pikminType", "pikminNumber"])
data class PikminRecord(
    val decorType: DecorType,
    val costumeType: CostumeType,
    val pikminType: PikminType,
    val pikminNumber: Int,
    val pikminStatus: PikminStatusType
) {
    fun isSamePikminRecord(record: PikminRecord): Boolean{
        if (decorType != record.decorType) return false
        if (costumeType != record.costumeType) return false
        if (pikminType != record.pikminType) return false
        if (pikminNumber != record.pikminNumber) return false
        return true
    }
}