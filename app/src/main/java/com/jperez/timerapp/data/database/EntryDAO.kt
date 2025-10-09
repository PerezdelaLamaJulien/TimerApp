package com.jperez.timerapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jperez.timerapp.data.database.entity.EntryEntity

@Dao
interface EntryDAO {
    
    @Query("SELECT * FROM entryEntity")
    suspend fun getAll(): List<EntryEntity>

    @Insert
    suspend fun insertAll(vararg entryEntity: EntryEntity)

    @Query("DELETE FROM entryentity WHERE id = :id")
    suspend fun deleteByID(id: String)

    @Query("DELETE FROM entryentity")
    suspend fun deleteAll()

}