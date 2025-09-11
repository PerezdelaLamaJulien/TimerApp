package com.jperez.timerapp

import android.Manifest
import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.jperez.timerapp.ui.theme.TimerAppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.Duration

class MainActivity : ComponentActivity() {

    private var mService: TimerService? = null

    private val _isPaused = MutableStateFlow(false)
    val isPaused: StateFlow<Boolean> = _isPaused

    private val _duration = MutableStateFlow(Duration.ZERO)
    val duration: StateFlow<Duration> = _duration

    /** Defines callbacks for service binding, passed to bindService().  */
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as TimerService.LocalBinder
            mService = binder.getService()
            val nameObserver = Observer<Duration> { newDuration ->
                _duration.value = newDuration
            }
            mService!!.durationLiveData.observe(this@MainActivity, nameObserver)
        }

        override fun onServiceDisconnected(arg0: ComponentName) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermissions(context = this@MainActivity)

        enableEdgeToEdge()
        setContent {
            val duration = duration.collectAsState()
            val isPaused = isPaused.collectAsState()
            TimerAppTheme {
                MainScreen(
                    duration = duration.value,
                    isPaused = isPaused.value,
                    onStart = {
                        val intent =
                            Intent(this@MainActivity, TimerService::class.java).apply {
                                this.action = "START_TIMER"
                            }
                        intent.putExtra("time", "demo")
                        ContextCompat.startForegroundService(this@MainActivity, intent)
                    },
                    onPaused = {
                        val intent =
                            Intent(this@MainActivity, TimerService::class.java).apply {
                                this.action = "PAUSE_TIMER"
                            }
                        intent.putExtra("time", "demo")
                        ContextCompat.startForegroundService(this@MainActivity, intent)
                    },
                    onStop = {
                        val intent =
                            Intent(this@MainActivity, TimerService::class.java).apply {
                                this.action = "STOP_TIMER"
                            }
                        intent.putExtra("time", "demo")
                        ContextCompat.startForegroundService(this@MainActivity, intent)
                    },

                )
            }
        }
    }


    private fun checkPermissions(context: Context) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1
            )
        }
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.FOREGROUND_SERVICE
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.FOREGROUND_SERVICE),
                2
            )
        }
    }

    override fun onStart() {
        super.onStart()
        // Bind to LocalService.
        Intent(this, TimerService::class.java).also { intent ->
            bindService(intent, connection, BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
    }
}