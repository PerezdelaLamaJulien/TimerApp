package com.jperez.timerapp.data.datasource

import com.jperez.timerapp.domain.model.Entry


interface EntryLocalDataSource {

    suspend fun getEntriesFromDatabase(): List<Entry>

    suspend fun saveEntryToDatabase(entry: Entry): Entry

    suspend fun deleteEntry(id: String)
}