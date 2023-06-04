package com.nkoyo.componentidentifier.database

import androidx.room.TypeConverter
import java.time.LocalDateTime

class DateConverter {
    @TypeConverter
    fun dateToString(date: LocalDateTime?): String? = date?.toString()

    @TypeConverter
    fun stringToDate(date: String?): LocalDateTime? = date?.let { LocalDateTime.parse(it) }
}