package com.jperez.timerapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CategoryDAO {
    
    @Query("SELECT * FROM categoryEntity")
    suspend fun getAll(): List<CategoryEntity>

    @Insert
    suspend fun insertAll(vararg entity: CategoryEntity)

    @Query("DELETE FROM categoryEntity WHERE id = :id")
    suspend fun deleteByID(id: String)
}