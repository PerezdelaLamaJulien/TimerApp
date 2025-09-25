package com.jperez.timerapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jperez.timerapp.data.database.converter.DurationConverter
import com.jperez.timerapp.data.database.converter.LocalDateTimeConverter
import com.jperez.timerapp.data.database.entity.CategoryEntity
import com.jperez.timerapp.data.database.entity.EntryEntity

@Database(entities = [EntryEntity::class, CategoryEntity::class], version = 1)
@TypeConverters(LocalDateTimeConverter::class, DurationConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun entryDAO(): EntryDAO
    abstract fun categoryDAO(): CategoryDAO
}