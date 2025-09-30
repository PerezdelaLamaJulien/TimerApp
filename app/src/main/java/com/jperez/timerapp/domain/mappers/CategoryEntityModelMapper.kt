package com.jperez.timerapp.domain.mappers

import com.jperez.timerapp.data.database.entity.CategoryEntity
import com.jperez.timerapp.domain.model.Category

class CategoryEntityModelMapper {

    fun mapEntityListToModelList(entities: List<CategoryEntity>) : List<Category>{
        return entities.map { categoryEntity ->
            mapEntityToModel(
                categoryEntity = categoryEntity
            )
        }
    }

    fun mapEntityToModel(categoryEntity: CategoryEntity): Category {
        return             Category(
            uid = categoryEntity.uid,
            name = categoryEntity.name,
            type = categoryEntity.type,
            color = categoryEntity.color
        )
    }
}