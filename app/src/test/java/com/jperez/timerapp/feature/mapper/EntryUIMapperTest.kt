package com.jperez.timerapp.feature.mapper

import com.jperez.timerapp.TestMockConstant
import com.jperez.timerapp.domain.model.Entry
import com.jperez.timerapp.feature.model.CategoryColor
import com.jperez.timerapp.feature.model.CategoryType
import com.jperez.timerapp.feature.model.EntryUI
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
    fun `map Entry to EntryUI`() = runTest {
        val result = mapper.mapEntryToEntryUI(
            Entry(
                date = LocalDateTime.of(2025, 1, 15, 12, 30),
                duration = 5.seconds.toJavaDuration(),
                category = TestMockConstant.outputCategory,
                description = ""
            )
        )
        assertEquals(
            EntryUI(
                date = "15 janvier 2025",
                duration = "5 secondes",
                description = "",
                color = CategoryColor.DARK_GREEN,
                categoryType = CategoryType.DEFAULT
            ), result
        )
    }

    @Test
    fun `format duration with only seconds`() = runTest {
        val result = mapper.formatDuration(5.seconds,)
        assertEquals("5 secondes", result)
    }

    @Test
    fun `format duration with only minutes`() = runTest {
        val result = mapper.formatDuration( 4.minutes,)
        assertEquals("4 minutes", result)
    }

    @Test
    fun `format duration with only hours`() = runTest {
        val result = mapper.formatDuration(3.hours)
        assertEquals( "3 heures", result)
    }

    @Test
    fun `format duration with hours minutes seconds`() = runTest {
        val result = mapper.formatDuration( 3736.seconds)
        assertEquals( "1 heure 2 minutes 16 secondes", result)
    }

    @Test
    fun `format duration with hours and minutes`() = runTest {
        val result = mapper.formatDuration(3780.seconds,)
        assertEquals( "1 heure 3 minutes", result)
    }

    @Test
    fun `format duration with hours and seconds`() = runTest {
        val result = mapper.formatDuration( 3604.seconds)
        assertEquals("1 heure 4 secondes", result)
    }

    @Test
    fun `format duration with minutes seconds`() = runTest {
        val result = mapper.formatDuration(61.seconds)
        assertEquals("1 minute 1 seconde", result
        )
    }
}