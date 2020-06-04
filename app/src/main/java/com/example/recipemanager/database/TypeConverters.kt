package com.example.recipemanager.database

import androidx.room.TypeConverter
import java.util.*

class TypeConverters {

    @TypeConverter
    fun epochToDate(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToEpoch(date: Date?): Long? {
        return date?.time
    }
}