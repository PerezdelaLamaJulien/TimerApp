package com.jperez.timerapp.domain.usecase

import com.jperez.timerapp.data.datasource.CategoryLocalDataSource
import org.koin.java.KoinJavaComponent.inject
import kotlin.getValue

class DeleteCategoryUseCase {

    private val localDataSource: CategoryLocalDataSource by inject(CategoryLocalDataSource::class.java)

    suspend fun execute(categoryId: String ) {
        localDataSource.deleteCategory(categoryId)
    }
}