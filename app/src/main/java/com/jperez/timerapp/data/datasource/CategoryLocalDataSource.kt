package com.jperez.timerapp.data.datasource

import com.jperez.timerapp.domain.model.Category


interface CategoryLocalDataSource {

    suspend fun getCategoriesFromDatabase(): List<Category>

    suspend fun saveCategoryToDatabase(category: Category): Category

    suspend fun deleteCategory(id: String)

    suspend fun deleteAll()
}