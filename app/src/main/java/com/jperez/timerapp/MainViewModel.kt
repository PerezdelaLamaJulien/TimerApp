package com.jperez.timerapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jperez.timerapp.datasource.EntryLocalDataSource
import com.jperez.timerapp.model.Entry
import com.jperez.timerapp.model.EntryUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import java.time.Duration
import java.time.LocalDateTime

class MainViewModel : ViewModel() {
    private val localDataSource: EntryLocalDataSource by inject(
        EntryLocalDataSource::class.java)

    private val entryUIMapper: EntryUIMapper by inject(
        EntryUIMapper::class.java)

    private val _uiState = MutableStateFlow<MutableList<EntryUI>>(mutableListOf())
    val uiState: StateFlow<List<EntryUI>> = _uiState

    init {
        getEntries()
    }
    fun registerTimer(duration: Duration, launchedDateTime: LocalDateTime){
        viewModelScope.launch {
            val savedEntry = localDataSource.saveEntryToDatabase(
                Entry(
                    date = launchedDateTime,
                    duration = duration,
                    description = "", //todo
                    category = ""
                )
            )
            _uiState.value.add(entryUIMapper.mapEntryToEntryUI(savedEntry))
        }
    }

    fun getEntries() {
        viewModelScope.launch {
            val entries =  localDataSource.getEntriesFromDatabase()
            _uiState.value = entries.map { entryUIMapper.mapEntryToEntryUI(it) }.toMutableList()
        }
    }
}