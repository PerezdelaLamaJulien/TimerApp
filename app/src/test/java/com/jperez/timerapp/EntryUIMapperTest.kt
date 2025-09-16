package com.jperez.timerapp

import com.jperez.timerapp.model.Entry
import com.jperez.timerapp.model.EntryUI
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

/**
 * Unit tests for the EntryUIMapper class.
 */
class EntryUIMapperTest {

    val mapper = EntryUIMapper()

    @Test
    fun `map EntryUI with only seconds`() = runTest {
        val result = mapper.mapEntryToEntryUI(
            Entry(
                date = LocalDateTime.of(2025, 1, 15, 12, 30),
                duration = 5.seconds.toJavaDuration()
            )
        )
        assertEquals(
            EntryUI(
                date = "15 janvier 2025",
                duration = "5 secondes"
            ), result
        )
    }

    @Test
    fun `map EntryUI with only minutes`() = runTest {
        val result = mapper.mapEntryToEntryUI(
            Entry(
                date = LocalDateTime.of(2025, 1, 15, 12, 30),
                duration = 4.minutes.toJavaDuration()
            )
        )
        assertEquals(
            EntryUI(
                date = "15 janvier 2025",
                duration = "4 minutes"
            ), result
        )
    }

    @Test
    fun `map EntryUI with only hours`() = runTest {
        val result = mapper.mapEntryToEntryUI(
            Entry(
                date = LocalDateTime.of(2025, 1, 15, 12, 30),
                duration = 3.hours.toJavaDuration()
            )
        )
        assertEquals(
            EntryUI(
                date = "15 janvier 2025",
                duration = "3 heures"
            ), result
        )
    }

    @Test
    fun `map EntryUI with hours minutes seconds`() = runTest {
        val result = mapper.mapEntryToEntryUI(
            Entry(
                date = LocalDateTime.of(2025, 1, 15, 12, 30),
                duration = 3736.seconds.toJavaDuration()
            )
        )
        assertEquals(
            EntryUI(
                date = "15 janvier 2025",
                duration = "1 heure 2 minutes 16 secondes"
            ), result
        )
    }

    @Test
    fun `map EntryUI with hours and minutes`() = runTest {
        val result = mapper.mapEntryToEntryUI(
            Entry(
                date = LocalDateTime.of(2025, 1, 15, 12, 30),
                duration = 3780.seconds.toJavaDuration()
            )
        )
        assertEquals(
            EntryUI(
                date = "15 janvier 2025",
                duration = "1 heure 3 minutes"
            ), result
        )
    }

    @Test
    fun `map EntryUI with hours and seconds`() = runTest {
        val result = mapper.mapEntryToEntryUI(
            Entry(
                date = LocalDateTime.of(2025, 1, 15, 12, 30),
                duration = 3604.seconds.toJavaDuration()
            )
        )
        assertEquals(
            EntryUI(
                date = "15 janvier 2025",
                duration = "1 heure 4 secondes"
            ), result
        )
    }

    @Test
    fun `map EntryUI with minutes seconds`() = runTest {
        val result = mapper.mapEntryToEntryUI(
            Entry(
                date = LocalDateTime.of(2025, 1, 15, 12, 30),
                duration = 61.seconds.toJavaDuration()
            )
        )
        assertEquals(
            EntryUI(
                date = "15 janvier 2025",
                duration = "1 minute 1 seconde"
            ), result
        )
    }
}