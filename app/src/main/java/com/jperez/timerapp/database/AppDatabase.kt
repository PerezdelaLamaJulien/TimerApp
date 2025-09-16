package com.jperez.timerapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [EntryEntity::class], version = 1)
@TypeConverters(LocalDateTimeConverter::class, DurationConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun entryDAO(): EntryDAO
}