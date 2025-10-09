package com.jperez.timerapp.feature.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jperez.timerapp.domain.model.Entry
import com.jperez.timerapp.domain.usecase.AddEntryUseCase
import com.jperez.timerapp.domain.usecase.CleanDatabaseUseCase
import com.jperez.timerapp.domain.usecase.DeleteCategoryUseCase
import com.jperez.timerapp.domain.usecase.GetCategoriesUseCase
import com.jperez.timerapp.domain.usecase.SaveCategoryUseCase
import com.jperez.timerapp.feature.composable.SettingsScreen
import com.jperez.timerapp.feature.mapper.CategoryUIMapper
import com.jperez.timerapp.feature.model.CategoryColor
import com.jperez.timerapp.feature.model.CategoryType
import com.jperez.timerapp.feature.model.CategoryUI
import com.jperez.timerapp.feature.model.SettingsScreenUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import java.time.LocalDateTime
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

class SettingsViewModel : ViewModel() {

    private val getCategoriesUseCase: GetCategoriesUseCase by inject(
        GetCategoriesUseCase::class.java
    )

    private val addEntryUseCase: AddEntryUseCase by inject(
        AddEntryUseCase::class.java
    )

    private val saveCategoryUseCase: SaveCategoryUseCase by inject(
        SaveCategoryUseCase::class.java
    )

    private val deleteCategoryUseCase: DeleteCategoryUseCase by inject(
        DeleteCategoryUseCase::class.java
    )

    private val cleanDatabaseUseCase: CleanDatabaseUseCase by inject(
        CleanDatabaseUseCase::class.java
    )
    private val categoryUIMapper: CategoryUIMapper by inject(
        CategoryUIMapper::class.java
    )

    private val _uiState = MutableStateFlow<SettingsScreenUI>(SettingsScreenUI())
    val uiState: StateFlow<SettingsScreenUI> = _uiState

    init {
        getCategories()
    }

    fun getCategories() {
        viewModelScope.launch {
            val entries = getCategoriesUseCase.execute()
            _uiState.value = uiState.value.copy(
                categories = entries.map { categoryUIMapper.mapCategoryToCategoryUI(it) }
                    .toMutableList()
            )
        }
    }

    fun saveCategory(category: CategoryUI) {
        viewModelScope.launch {
            saveCategoryUseCase.execute(
                categoryUIMapper.reverseMapCategoryUIToCategory(category)
            )
            val updatedIndex = uiState.value.categories.indexOfFirst { it.id == category.id }
            if (updatedIndex != -1) {
                //todo category doesn't have id here so you can't update it after adding it
                _uiState.value = uiState.value.copy(
                    categories = uiState.value.categories.toMutableList()
                        .apply { this[updatedIndex] = category }
                )
            } else {
                _uiState.value = uiState.value.copy(
                    categories = uiState.value.categories.toMutableList()
                        .apply { this.add(category) }
                )
            }
        }
    }

    fun deleteCategory(categoryId: String) {
        viewModelScope.launch {
            deleteCategoryUseCase.execute(categoryId)
            _uiState.value = uiState.value.copy(
                categories = uiState.value.categories.toMutableList().apply {
                    this.removeAt(uiState.value.categories.indexOfFirst { it.id == categoryId })
                }
            )
        }
    }

    fun cleanDatabase() {
        viewModelScope.launch {
            val result = cleanDatabaseUseCase.execute()
            if (result) {
                _uiState.value = uiState.value.copy(
                    categories = mutableListOf(),
                    snackbarText = "Database has been cleaned"
                )
            } else {
                _uiState.value = uiState.value.copy(
                    snackbarText = "Something wrong happens"
                )
            }
        }
    }

    fun snackBarDismissed() {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(
                snackbarText = null
            )
        }
    }

    fun categoryDemoMode() {
        viewModelScope.launch {
            saveCategory(
                CategoryUI(
                    id = null,
                    name = "Lecture",
                    categoryType = CategoryType.READ,
                    color = CategoryColor.DARK_GREEN
                )
            )

            saveCategory(
                CategoryUI(
                    id = null,
                    name = "Gaming",
                    categoryType = CategoryType.GAME,
                    color = CategoryColor.KEN_DARK_ORANGE
                )
            )

            saveCategory(
                CategoryUI(
                    id = null,
                    name = "Dev",
                    categoryType = CategoryType.DEV,
                    color = CategoryColor.FUUKA_BLUE
                )
            )

            saveCategory(
                CategoryUI(
                    id = null,
                    name = "Japonais",
                    categoryType = CategoryType.STUDY,
                    color = CategoryColor.KORO_LIGHT_GRAY
                )
            )

            saveCategory(
                CategoryUI(
                    id = null,
                    name = "Workout",
                    categoryType = CategoryType.WORKOUT,
                    color = CategoryColor.MITSURU_RED
                )
            )

            saveCategory(
                CategoryUI(
                    id = null,
                    name = "Dev",
                    categoryType = CategoryType.COOK,
                    color = CategoryColor.YUKARI_PINK
                )
            )
        }
    }

        fun entriesDemoMode() {
            viewModelScope.launch {

                val categories = getCategoriesUseCase.execute()

                addEntryUseCase.execute(
                    Entry(
                        date = LocalDateTime.of(2025, 2, 7, 8, 16),
                        duration = 4324.seconds.toJavaDuration(),
                        category = categories.first { it.type == "WORKOUT" },
                        description = "Workout"
                    )
                )

                addEntryUseCase.execute(
                    Entry(
                        date = LocalDateTime.of(2025, 2, 7, 22, 4),
                        duration = 4994.seconds.toJavaDuration(),
                        category = categories.first { it.type == "DEV" },
                        description = "Timer App"
                    )
                )

                addEntryUseCase.execute(
                    Entry(
                        date = LocalDateTime.of(2025, 2, 8, 8, 47),
                        duration = 2120.seconds.toJavaDuration(),
                        category = categories.first { it.type == "STUDY" },
                        description = "Duolingo Japonais"
                    )
                )

                addEntryUseCase.execute(
                    Entry(
                        date = LocalDateTime.of(2025, 2, 8, 11, 35),
                        duration = 4994.seconds.toJavaDuration(),
                        category = categories.first { it.type == "COOK" },
                        description = "Riz / Champi / Chataignes"
                    )
                )

                addEntryUseCase.execute(
                    Entry(
                        date = LocalDateTime.of(2025, 2, 8, 19, 2),
                        duration = 2895.seconds.toJavaDuration(),
                        category = categories.first { it.type == "GAME" },
                        description = "Ghost of Tsushima"
                    )
                )

                addEntryUseCase.execute(
                    Entry(
                        date = LocalDateTime.now().minusHours(2),
                        duration = 1648.seconds.toJavaDuration(),
                        category = categories.first { it.type == "READ" },
                        description = "Dune"
                    )
                )

                _uiState.value = uiState.value.copy(
                    snackbarText = "Demo data has been cloned"
                )
            }
        }

}