package com.jperez.timerapp.data.database.converter

import androidx.room.TypeConverter
import java.time.Duration

class DurationConverter {
    @TypeConverter
    fun fromDuration(value: Duration?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toDuration(date: String?): Duration? {
        return Duration.parse(date)
    }
}