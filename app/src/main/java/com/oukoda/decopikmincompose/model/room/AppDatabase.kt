package com.oukoda.decopikmincompose.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.oukoda.decopikmincompose.model.room.dao.PikminRecordDao
import com.oukoda.decopikmincompose.model.room.entity.PikminRecord

@Database(entities = [PikminRecord::class], version = 1)
@TypeConverters(RoomConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pikminRecordDao(): PikminRecordDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "pikmin_database"
                    ).fallbackToDestructiveMigration()
                        .addTypeConverter(RoomConverter())
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}