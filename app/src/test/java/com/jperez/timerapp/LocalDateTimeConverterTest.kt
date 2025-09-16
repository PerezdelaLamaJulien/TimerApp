package com.jperez.timerapp

import com.jperez.timerapp.database.LocalDateTimeConverter
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

/**
 * Unit tests for the LocalDateTimeConverter class.
 */
class LocalDateTimeConverterTest {

    val converter = LocalDateTimeConverter()

    @Test
    fun `from LocalDateTime`() = runTest {
        val result = converter.fromLocalDateTime(
            value = LocalDateTime.of(2025, 1, 15, 12, 30)
        )
        assertEquals("2025-01-15T12:30", result)
    }

    @Test
    fun `to LocalDateTime`() = runTest {
        val result = converter.toLocalDateTime(
            "2025-01-15T12:30"
        )
        assertEquals(LocalDateTime.of(2025, 1, 15, 12, 30), result)
    }
}