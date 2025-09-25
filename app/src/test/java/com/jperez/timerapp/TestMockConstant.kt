package com.jperez.timerapp

import com.jperez.timerapp.data.database.entity.EntryEntity
import com.jperez.timerapp.domain.model.Category
import com.jperez.timerapp.domain.model.Entry
import com.jperez.timerapp.feature.model.CategoryColor
import com.jperez.timerapp.feature.model.CategoryType
import com.jperez.timerapp.feature.model.EntryUI
import java.time.LocalDateTime
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

class TestMockConstant {

    companion object {
        val inputEntry = Entry(
            date = LocalDateTime.of(2025, 1, 15, 12, 30),
            duration = 5.seconds.toJavaDuration(),
            category = "",
            description = ""
        )

        val outputEntry = inputEntry.copy(
            uid = "uid"
        )

        val entryUI = EntryUI(
            date = "15 janvier 2025",
            duration = "5 secondes",
            description = "",
            color = CategoryColor.PERSONA5_YELLOW,
            categoryType = CategoryType.DEFAULT
        )

        val entryEntity = EntryEntity(
            date = LocalDateTime.of(2025, 1, 15, 12, 30),
            duration = 5.seconds.toJavaDuration(),
            uid = "uid",
            category = "",
            description = null
        )

        val inputCategory = Category(
            uid = null,
            name = "Category",
            type = "DEFAULT",
            color = "DARK_GREEN"
        )

        val outputCategory = inputCategory.copy(
            uid = "uid"
        )
    }
}