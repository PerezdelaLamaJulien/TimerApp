package com.jperez.timerapp.feature.model

data class EntryUI(
    val date: String,
    val duration: String,
    val description: String,
    val color : CategoryColor,
    val categoryType : CategoryType,
)
