package com.jperez.timerapp.domain.usecase

import com.jperez.timerapp.data.datasource.CategoryLocalDataSource
import org.koin.java.KoinJavaComponent.inject
import kotlin.getValue

class CleanDatabaseUseCase {

    private val categoryDataSource: CategoryLocalDataSource by inject(CategoryLocalDataSource::class.java)

    suspend fun execute(): Boolean {
        try {
            categoryDataSource.deleteAll()
            return true
        } catch (ex: Exception){
            throw ex
        }
    }
}