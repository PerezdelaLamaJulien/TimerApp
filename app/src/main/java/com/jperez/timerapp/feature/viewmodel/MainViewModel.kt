package com.jperez.timerapp.feature.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jperez.timerapp.domain.model.Entry
import com.jperez.timerapp.domain.usecase.AddEntryUseCase
import com.jperez.timerapp.domain.usecase.GetEntriesUseCase
import com.jperez.timerapp.feature.mapper.EntryUIMapper
import com.jperez.timerapp.feature.model.EntryUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import java.time.Duration
import java.time.LocalDateTime

class MainViewModel : ViewModel() {

    private val addEntryUseCase: AddEntryUseCase by inject(
        AddEntryUseCase::class.java)

    private val getEntriesUseCase: GetEntriesUseCase by inject(
        GetEntriesUseCase::class.java)

    private val entryUIMapper: EntryUIMapper by inject(
        EntryUIMapper::class.java)

    private val _uiState = MutableStateFlow<MutableList<EntryUI>>(mutableListOf())
    val uiState: StateFlow<List<EntryUI>> = _uiState

    init {
        getEntries()
    }
    fun registerTimer(duration: Duration, launchedDateTime: LocalDateTime){
        viewModelScope.launch {
            val savedEntry = addEntryUseCase.execute(
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
            _uiState.value = getEntriesUseCase.execute().map { entryUIMapper.mapEntryToEntryUI(it) }.toMutableList()
        }
    }
}