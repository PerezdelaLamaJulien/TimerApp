package com.jperez.timerapp.feature.model

data class MainScreenUI(
    val entries : MutableList<EntryUI> = mutableListOf(),
    val categories : MutableList<CategoryUI> = mutableListOf(),
    val selectedCategory: CategoryUI? = null
)
