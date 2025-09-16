package com.jperez.timerapp

import com.jperez.timerapp.database.DurationConverter
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

/**
 * Unit tests for the DurationConverter class.
 */
class DurationConverterTest {

    val converter = DurationConverter()

    @Test
    fun `from Duration`() = runTest {
        val result = converter.fromDuration(
            value = 5.seconds.toJavaDuration()
        )
        assertEquals("PT5S", result)
    }

    @Test
    fun `to Duration`() = runTest {
        val result = converter.toDuration(
            "PT5S"
        )
        assertEquals(5.seconds.toJavaDuration(), result)
    }
}