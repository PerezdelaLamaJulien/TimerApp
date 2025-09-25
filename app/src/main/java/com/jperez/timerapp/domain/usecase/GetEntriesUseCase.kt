package com.jperez.timerapp.domain.usecase

import com.jperez.timerapp.data.datasource.EntryLocalDataSource
import com.jperez.timerapp.domain.model.Entry
import org.koin.java.KoinJavaComponent.inject
import kotlin.getValue

class GetEntriesUseCase {

    private val localDataSource: EntryLocalDataSource by inject(EntryLocalDataSource::class.java)

    suspend fun execute() : List<Entry> {
        return localDataSource.getEntriesFromDatabase()
    }
}