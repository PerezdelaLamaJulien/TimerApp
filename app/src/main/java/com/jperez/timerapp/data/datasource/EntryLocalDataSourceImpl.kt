package com.jperez.timerapp.data.datasource

import com.jperez.timerapp.data.database.EntryDAO
import com.jperez.timerapp.data.database.entity.EntryEntity
import com.jperez.timerapp.domain.mappers.EntryEntityModelMapper
import com.jperez.timerapp.domain.model.Entry
import org.koin.java.KoinJavaComponent.inject
import java.util.UUID

class EntryLocalDataSourceImpl : EntryLocalDataSource {
    private val entryDAO: EntryDAO by inject(EntryDAO::class.java)
    private val entryEntityModelMapper: EntryEntityModelMapper by inject(EntryEntityModelMapper::class.java)

    override suspend fun getEntriesFromDatabase(): List<Entry> {
        val entries =  entryDAO.getAll()
        return entryEntityModelMapper.mapEntityListToModelList(entries)
    }

    override suspend fun saveEntryToDatabase(entry: Entry): Entry {
        val id = UUID.randomUUID().toString()
        entryDAO.insertAll(
            EntryEntity(
                uid = id,
                date = entry.date,
                duration = entry.duration,
                category = entry.category.uid!!,
                description = entry.description
            )
        )

        return entry.copy(uid = id)
    }

    override suspend fun deleteEntry(id: String) {
        entryDAO.deleteByID(id)
    }

    override suspend fun deleteAll() {
        entryDAO.deleteAll()
    }
}