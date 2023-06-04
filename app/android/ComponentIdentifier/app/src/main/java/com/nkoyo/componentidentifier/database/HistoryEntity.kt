package com.nkoyo.componentidentifier.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime


@Entity
data class HistoryEntity(
    @PrimaryKey val historyId: Long,
    val componentName: String,
    val imageUrl: String,
    val dateTime: LocalDateTime,
)