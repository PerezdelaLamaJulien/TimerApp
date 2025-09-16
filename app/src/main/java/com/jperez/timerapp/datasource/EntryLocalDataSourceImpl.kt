package com.jperez.timerapp.datasource

import com.jperez.timerapp.database.EntryDAO
import com.jperez.timerapp.database.EntryEntity
import com.jperez.timerapp.model.Entry
import org.koin.java.KoinJavaComponent.inject
import java.util.UUID

class EntryLocalDataSourceImpl : EntryLocalDataSource {
    private val entryDAO: EntryDAO by inject(EntryDAO::class.java)

    override suspend fun getEntriesFromDatabase(): List<Entry> {
        return entryDAO.getAll().map { it ->
            Entry(
                it.uid,
                it.date,
                it.duration
            )
        }
    }

    override suspend fun saveEntryToDatabase(entry: Entry): Entry {
        val id = UUID.randomUUID().toString()
        entryDAO.insertAll(EntryEntity(
            id,
            entry.date,
            entry.duration
        ))

        return entry.copy(uid = id)
    }

    override suspend fun deleteEntry(id: String) {
        entryDAO.deleteByID(id)
    }
}