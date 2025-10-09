package com.jperez.timerapp.feature.model

data class SettingsScreenUI(
    val categories : MutableList<CategoryUI> = mutableListOf(),
    val snackbarText: String? = null,
)
