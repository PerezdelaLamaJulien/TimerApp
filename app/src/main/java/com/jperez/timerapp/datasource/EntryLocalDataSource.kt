package com.jperez.timerapp.datasource

import com.jperez.timerapp.model.Category
import com.jperez.timerapp.model.Entry


interface EntryLocalDataSource {

    suspend fun getEntriesFromDatabase(): List<Entry>

    suspend fun saveEntryToDatabase(entry: Entry): Entry

    suspend fun deleteEntry(id: String)


    suspend fun getCategoriesFromDatabase(): List<Category>

    suspend fun saveCategoryToDatabase(entry: Category): Category

    suspend fun deleteCategory(id: String)
}