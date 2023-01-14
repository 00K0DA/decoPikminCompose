package com.oukoda.decopikmincompose.model.room

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.oukoda.decopikmincompose.model.enumclass.CostumeType
import com.oukoda.decopikmincompose.model.enumclass.DecorType
import com.oukoda.decopikmincompose.model.enumclass.PikminStatusType
import com.oukoda.decopikmincompose.model.enumclass.PikminType
import kotlin.math.cos

@ProvidedTypeConverter
class RoomConverter {
    @TypeConverter
    fun decorTypeToString(decorType: DecorType): String{
        return decorType.name
    }

    @TypeConverter
    fun stringToDecorType(string: String): DecorType{
        return DecorType.valueOf(string)
    }

    @TypeConverter
    fun costumeTypeToString(costumeType: CostumeType): String{
        return costumeType.name
    }

    @TypeConverter
    fun stringToCostumeType(string: String): CostumeType{
        return CostumeType.valueOf(string)
    }

    @TypeConverter
    fun pikminTypeToString(pikminType: PikminType): String{
        return pikminType.name
    }

    @TypeConverter
    fun stringToPikminType(string: String): PikminType{
        return PikminType.valueOf(string)
    }

    @TypeConverter
    fun pikminStatusTypeToInt(pikminStatusType: PikminStatusType): Int{
        return pikminStatusType.value
    }

    @TypeConverter
    fun stringToPikminStatusType(int: Int): PikminStatusType{
        return PikminStatusType.create(int)
    }
}