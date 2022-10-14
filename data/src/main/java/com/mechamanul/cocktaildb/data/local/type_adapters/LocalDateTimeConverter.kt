package com.mechamanul.cocktaildb.data.local.type_adapters
import androidx.room.TypeConverter
import java.util.*

class LocalDateTimeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}