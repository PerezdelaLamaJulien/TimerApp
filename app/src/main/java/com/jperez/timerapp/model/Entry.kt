package com.jperez.timerapp.model

import java.time.Duration
import java.time.LocalDateTime

data class Entry(
    val uid: String? = null,
    val date: LocalDateTime,
    val duration: Duration,
    val category: String,
    val description: String?,
)
