package com.jperez.timerapp.model

import com.jperez.timerapp.CategoryColor
import com.jperez.timerapp.CategoryType

data class EntryUI(
    val date: String,
    val duration: String,
    val description: String,
    val color : CategoryColor,
    val categoryType : CategoryType,
)
