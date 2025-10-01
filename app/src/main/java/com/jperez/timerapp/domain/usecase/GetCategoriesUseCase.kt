package com.jperez.timerapp.domain.usecase

import com.jperez.timerapp.data.datasource.CategoryLocalDataSource
import com.jperez.timerapp.domain.model.Category
import org.koin.java.KoinJavaComponent.inject

class GetCategoriesUseCase {

    private val localDataSource: CategoryLocalDataSource by inject(CategoryLocalDataSource::class.java)

    suspend fun execute() : List<Category> {
        return localDataSource.getCategoriesFromDatabase()
    }

}