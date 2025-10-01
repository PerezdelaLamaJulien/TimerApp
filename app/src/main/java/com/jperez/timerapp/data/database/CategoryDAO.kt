package com.jperez.timerapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jperez.timerapp.data.database.entity.CategoryEntity

@Dao
interface CategoryDAO {
    
    @Query("SELECT * FROM categoryEntity")
    suspend fun getAll(): List<CategoryEntity>

    @Insert
    suspend fun insertCategory(vararg entity: CategoryEntity)

    @Update
    suspend fun updateCategory(vararg entity: CategoryEntity)

    @Query("DELETE FROM categoryEntity WHERE id = :id")
    suspend fun deleteByID(id: String)
}