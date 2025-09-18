package com.jperez.timerapp.datasource

import com.jperez.timerapp.database.CategoryDAO
import com.jperez.timerapp.database.CategoryEntity
import com.jperez.timerapp.database.EntryDAO
import com.jperez.timerapp.database.EntryEntity
import com.jperez.timerapp.model.Category
import com.jperez.timerapp.model.Entry
import org.koin.java.KoinJavaComponent.inject
import java.util.UUID

class EntryLocalDataSourceImpl : EntryLocalDataSource {
    private val entryDAO: EntryDAO by inject(EntryDAO::class.java)
    private val categoryDAO: CategoryDAO by inject(CategoryDAO::class.java)

    override suspend fun getEntriesFromDatabase(): List<Entry> {
        return entryDAO.getAll().map { it ->
            Entry(
                uid = it.uid,
                date = it.date,
                duration = it.duration,
                category = it.category,
                description = it.description
            )
        }
    }

    override suspend fun saveEntryToDatabase(entry: Entry): Entry {
        val id = UUID.randomUUID().toString()
        entryDAO.insertAll(
            EntryEntity(
                uid = id,
                date = entry.date,
                duration = entry.duration,
                category = entry.category,
                description = entry.description
            )
        )

        return entry.copy(uid = id)
    }

    override suspend fun deleteEntry(id: String) {
        entryDAO.deleteByID(id)
    }

    override suspend fun getCategoriesFromDatabase(): List<Category> {
        return categoryDAO.getAll().map { it ->
            Category(
                uid = it.uid,
                name = it.name,
                type = it.type,
                color = it.color
            )
        }
    }

    override suspend fun saveCategoryToDatabase(entry: Category): Category {
        val id = UUID.randomUUID().toString()
        categoryDAO.insertAll(
            CategoryEntity(
                uid = id,
                name = entry.name,
                type = entry.type,
                color = entry.color
            )
        )

        return entry.copy(uid = id)
    }

    override suspend fun deleteCategory(id: String) {
        categoryDAO.deleteByID(id)
    }
}