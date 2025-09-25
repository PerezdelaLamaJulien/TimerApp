package com.jperez.timerapp.feature.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jperez.timerapp.domain.model.Category
import com.jperez.timerapp.domain.usecase.GetCategoriesUseCase
import com.jperez.timerapp.domain.usecase.SaveCategoryUseCase
import com.jperez.timerapp.feature.mapper.CategoryUIMapper
import com.jperez.timerapp.feature.model.CategoryUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class SettingsViewModel : ViewModel() {

    private val getCategoriesUseCase: GetCategoriesUseCase by inject(
        GetCategoriesUseCase::class.java)

    private val saveCategoryUseCase: SaveCategoryUseCase by inject(
        SaveCategoryUseCase::class.java)

    private val categoryUIMapper: CategoryUIMapper by inject(
        CategoryUIMapper::class.java)

    private val _uiState = MutableStateFlow<MutableList<CategoryUI>>(mutableListOf())
    val uiState: StateFlow<List<CategoryUI>> = _uiState

    init {
        getCategories()
    }

    fun getCategories() {
        viewModelScope.launch {
            val entries =  getCategoriesUseCase.execute()
            _uiState.value = entries.map { categoryUIMapper.mapCategoryToCategoryUI(it) }.toMutableList()
        }
    }

    fun saveCategory(category : CategoryUI) {
        viewModelScope.launch {
            saveCategoryUseCase.execute(Category(
                name = category.name,
                type = category.categoryType.name,
                color = category.color.name,
            ))
            _uiState.value.add(category)
        }
    }
}