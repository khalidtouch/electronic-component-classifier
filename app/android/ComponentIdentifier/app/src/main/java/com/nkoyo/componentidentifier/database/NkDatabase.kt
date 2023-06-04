package com.nkoyo.componentidentifier.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters


@Database(entities = [HistoryEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class NkDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}