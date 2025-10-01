package com.jperez.timerapp.feature.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jperez.timerapp.domain.model.Entry
import com.jperez.timerapp.domain.usecase.AddEntryUseCase
import com.jperez.timerapp.domain.usecase.GetCategoriesUseCase
import com.jperez.timerapp.domain.usecase.GetEntriesUseCase
import com.jperez.timerapp.feature.mapper.CategoryUIMapper
import com.jperez.timerapp.feature.mapper.EntryUIMapper
import com.jperez.timerapp.feature.model.CategoryUI
import com.jperez.timerapp.feature.model.MainScreenUI
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

    private val getCategoriesUseCase: GetCategoriesUseCase by inject(
        GetCategoriesUseCase::class.java)

    private val entryUIMapper: EntryUIMapper by inject(
        EntryUIMapper::class.java)

    private val categoryUIMapper: CategoryUIMapper by inject(
        CategoryUIMapper::class.java)


    private val _uiState = MutableStateFlow(MainScreenUI())
    val uiState: StateFlow<MainScreenUI> = _uiState

    init {
        initScreenState()
    }

    fun initScreenState() {
        viewModelScope.launch {
            val entries = getEntriesUseCase.execute().map { entryUIMapper.mapEntryToEntryUI(it) }.toMutableList()
            val categories = getCategoriesUseCase.execute().map { categoryUIMapper.mapCategoryToCategoryUI(it) }.toMutableList()

            _uiState.value = uiState.value.copy(
                entries =  entries,
                categories = categories,
                selectedCategory = categories.first()
            )
        }
    }

    fun registerTimer(duration: Duration, launchedDateTime: LocalDateTime){
        viewModelScope.launch {
            val savedEntry = addEntryUseCase.execute(
                Entry(
                    date = launchedDateTime,
                    duration = duration,
                    description = "", //todo
                    category = categoryUIMapper.reverseMapCategoryUIToCategory(uiState.value.selectedCategory!!)
                )
            )

            _uiState.value = uiState.value.copy(
                entries = uiState.value.entries.toMutableList().apply { add(entryUIMapper.mapEntryToEntryUI(savedEntry)) },
            )
        }
    }

    fun selectedCategoryChanged(categoryUI: CategoryUI){
        _uiState.value = uiState.value.copy(
            selectedCategory = categoryUI
        )
    }
}