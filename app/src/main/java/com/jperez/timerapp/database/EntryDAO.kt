package com.jperez.timerapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EntryDAO {
    
    @Query("SELECT * FROM entryEntity")
    suspend fun getAll(): List<EntryEntity>

    @Insert
    suspend fun insertAll(vararg entryEntity: EntryEntity)

    @Query("DELETE FROM entryentity WHERE id = :id")
    suspend fun deleteByID(id: String)

}