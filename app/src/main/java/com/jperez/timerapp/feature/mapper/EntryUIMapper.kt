package com.jperez.timerapp.feature.mapper

import com.jperez.timerapp.domain.model.Entry
import com.jperez.timerapp.feature.model.CategoryColor
import com.jperez.timerapp.feature.model.CategoryType
import com.jperez.timerapp.feature.model.EntryUI
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.time.Duration
import kotlin.time.toKotlinDuration

class EntryUIMapper {

    fun formatDuration(duration: Duration): String {
        var durationString = ""

        duration.toComponents { hours, minutes, seconds, nano ->
            if (hours != 0L) {
                durationString += if (hours == 1L) {
                    "$hours heure"
                } else {
                    "$hours heures"
                }
            }
            if (minutes != 0) {
                if (durationString != "") {
                    durationString += " "
                }

                durationString += if (minutes == 1) {
                    "$minutes minute"
                } else {
                    "$minutes minutes"
                }
            }
            if (seconds != 0) {
                if (durationString != "") {
                    durationString += " "
                }

                durationString += if (seconds == 1) {
                    "$seconds seconde"
                } else {
                    "$seconds secondes"
                }
            }
        }
        return durationString
    }

    fun mapEntryToEntryUI(entry: Entry): EntryUI {
        return EntryUI(
            date = entry.date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)),
            duration = formatDuration(entry.duration.toKotlinDuration()),
            description = entry.description ?: "",
            color = CategoryColor.DARK_GREEN,
            categoryType = CategoryType.DEFAULT,
        )
    }
}