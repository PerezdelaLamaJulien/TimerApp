package com.jperez.timerapp.domain.mappers

import com.jperez.timerapp.data.database.CategoryDAO
import com.jperez.timerapp.data.database.entity.CategoryEntity
import com.jperez.timerapp.data.database.entity.EntryEntity
import com.jperez.timerapp.domain.model.Entry
import org.koin.java.KoinJavaComponent.inject
import kotlin.getValue

class EntryEntityModelMapper {

    private val categoryDAO: CategoryDAO by inject(CategoryDAO::class.java)
    private val categoryEntityModelMapper: CategoryEntityModelMapper by inject(CategoryEntityModelMapper::class.java)

    suspend fun mapEntityListToModelList(entities: List<EntryEntity>) : List<Entry>{
        val categories = categoryDAO.getAll()
        return entities.map { entryEntity ->
            mapEntityToModel(
                entryEntity = entryEntity,
                categoryEntity = categories.first { it.uid  == entryEntity.category }
            )
        }
    }

    fun mapEntityToModel(entryEntity : EntryEntity, categoryEntity: CategoryEntity): Entry {
        return Entry(
            uid = entryEntity.uid,
            date = entryEntity.date,
            duration = entryEntity.duration,
            category = categoryEntityModelMapper.mapEntityToModel(categoryEntity),
            description = entryEntity.description
        )
    }
}