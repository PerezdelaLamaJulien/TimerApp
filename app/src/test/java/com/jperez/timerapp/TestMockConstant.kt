package com.jperez.timerapp

import com.jperez.timerapp.database.EntryEntity
import com.jperez.timerapp.model.Entry
import com.jperez.timerapp.model.EntryUI
import java.time.LocalDateTime
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

class TestMockConstant {

    companion object {
        val entry = Entry(
            date = LocalDateTime.of(2025, 1, 15, 12, 30),
            duration = 5.seconds.toJavaDuration()
        )
        val entryUI = EntryUI(
            date = "15 janvier 2025",
            duration = "5 secondes"
        )

        val entryEntity = EntryEntity(
            date = LocalDateTime.of(2025, 1, 15, 12, 30),
            duration = 5.seconds.toJavaDuration(),
            uid = "uid"
        )
    }
}