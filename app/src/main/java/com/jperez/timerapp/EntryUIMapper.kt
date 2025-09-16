package com.jperez.timerapp

import com.jperez.timerapp.model.Entry
import com.jperez.timerapp.model.EntryUI
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.time.toKotlinDuration

class EntryUIMapper {

    fun mapEntryToEntryUI(entry: Entry): EntryUI {
        var durationString = ""

        entry.duration.toKotlinDuration().toComponents { hours, minutes, seconds, nano ->
            if (hours != 0L) {
                durationString += if(hours == 1L){
                    "$hours heure"
                } else {
                    "$hours heures"
                }
            }
            if (minutes != 0) {
                if(durationString != ""){
                    durationString += " "
                }

                durationString += if(minutes == 1){
                    "$minutes minute"
                } else {
                    "$minutes minutes"
                }
            }
            if (seconds != 0) {
                if(durationString != ""){
                    durationString += " "
                }

                durationString += if(seconds == 1){
                    "$seconds seconde"
                } else {
                    "$seconds secondes"
                }
            }
        }
            return EntryUI(
            date = entry.date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)),
            duration = durationString
        )
    }
}
