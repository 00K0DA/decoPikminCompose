package com.oukoda.decopikmincompose.model.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.oukoda.decopikmincompose.model.enumclass.DecorType
import com.oukoda.decopikmincompose.model.room.entity.PikminRecord

@Dao
interface PikminRecordDao {
    @Query("SELECT * FROM pikminrecord WHERE decorType == (:decorType)")
    fun selectByDecorType(decorType: DecorType): List<PikminRecord>

    @Insert
    fun insert(pikminRecord: PikminRecord)

    @Update
    fun update(pikminRecord: PikminRecord)

    @Delete
    fun delete(pikminRecord: PikminRecord)
}