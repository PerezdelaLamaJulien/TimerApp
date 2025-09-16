package com.jperez.timerapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Duration
import java.time.LocalDateTime

@Entity
data class EntryEntity(
    @PrimaryKey @ColumnInfo(name = "id") val uid: String,
    @ColumnInfo(name = "date") val date: LocalDateTime,
    @ColumnInfo(name = "duration") val duration: Duration,
)