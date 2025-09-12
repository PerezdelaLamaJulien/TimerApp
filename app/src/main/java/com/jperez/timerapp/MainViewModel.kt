package com.jperez.timerapp

import androidx.lifecycle.ViewModel
import java.time.Duration
import java.time.LocalDateTime

class MainViewModel : ViewModel() {


    fun registerTimer(duration: Duration, launchedDateTime: LocalDateTime){
        println("duration : $duration - launchedDateTime : $launchedDateTime")
    }
}