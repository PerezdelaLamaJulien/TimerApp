package com.jperez.timerapp.domain.usecase

import com.jperez.timerapp.data.datasource.CategoryLocalDataSource
import com.jperez.timerapp.domain.model.Category
import org.koin.java.KoinJavaComponent.inject
import kotlin.getValue

class SaveCategoryUseCase {

    private val localDataSource: CategoryLocalDataSource by inject(CategoryLocalDataSource::class.java)

    suspend fun execute(category: Category) : Category {
        return localDataSource.saveCategoryToDatabase(category)
    }
}