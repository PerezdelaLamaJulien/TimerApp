package com.jperez.timerapp.data.datasource

import com.jperez.timerapp.data.database.CategoryDAO
import com.jperez.timerapp.data.database.entity.CategoryEntity
import com.jperez.timerapp.domain.mappers.CategoryEntityModelMapper
import com.jperez.timerapp.domain.model.Category
import org.koin.java.KoinJavaComponent.inject
import java.util.UUID
import kotlin.getValue

class CategoryLocalDataSourceImpl : CategoryLocalDataSource {
    private val categoryDAO: CategoryDAO by inject(CategoryDAO::class.java)
    private val categoryEntityModelMapper: CategoryEntityModelMapper by inject(CategoryEntityModelMapper::class.java)

    override suspend fun getCategoriesFromDatabase(): List<Category> {
        return categoryEntityModelMapper.mapEntityListToModelList(categoryDAO.getAll())
    }

    override suspend fun saveCategoryToDatabase(category: Category): Category {
        if(category.uid == null){
            val id = UUID.randomUUID().toString()
            categoryDAO.insertCategory(
                CategoryEntity(
                    uid = id,
                    name = category.name,
                    type = category.type,
                    color = category.color
                )
            )

            return category.copy(uid = id)
        } else {
            categoryDAO.updateCategory(
                CategoryEntity(
                    uid = category.uid,
                    name = category.name,
                    type = category.type,
                    color = category.color
                )
            )
            return category
        }
    }

    override suspend fun deleteCategory(id: String) {
        categoryDAO.deleteByID(id)
    }

    override suspend fun deleteAll() {
        categoryDAO.deleteAll()
    }
}