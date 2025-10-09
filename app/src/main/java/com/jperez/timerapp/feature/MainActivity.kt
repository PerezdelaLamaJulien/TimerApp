package com.jperez.timerapp.feature

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
import androidx.core.view.WindowCompat
import androidx.lifecycle.Observer
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jperez.timerapp.feature.composable.MainScreen
import com.jperez.timerapp.feature.composable.SettingsScreen
import com.jperez.timerapp.feature.model.Main
import com.jperez.timerapp.feature.model.Settings
import com.jperez.timerapp.feature.service.TimerService
import com.jperez.timerapp.feature.viewmodel.MainViewModel
import com.jperez.timerapp.ui.theme.TimerAppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {

    val viewModel: MainViewModel by viewModel()

    private val _isPaused = MutableStateFlow(false)
    val isPaused: StateFlow<Boolean> = _isPaused

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description

    private val _duration = MutableStateFlow(Duration.ZERO)
    val duration: StateFlow<Duration> = _duration

    private var launchTimerDateTime: LocalDateTime? = null
    private var mService: TimerService? = null
    private val durationObserver: Observer<Duration> = Observer { newDuration ->
        _duration.value = newDuration
    }

    private val instantObserver: Observer<Instant?> = Observer { instantStop ->
        if (instantStop != null) {
            viewModel.registerTimer(
                duration = duration.value,
                launchedDateTime = launchTimerDateTime ?: LocalDateTime.now(),
                description = description.value
            )
        }
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as TimerService.LocalBinder
            mService = binder.getService()
            mService!!.durationLiveData.observe(this@MainActivity, durationObserver)
            mService!!.instantTimerStopLiveData.observe(this@MainActivity, instantObserver)
        }

        override fun onServiceDisconnected(arg0: ComponentName) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermissions(context = this@MainActivity)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            TimerAppTheme {
                NavHost(navController = navController, startDestination = Main) {
                    composable<Main> {

                        MainScreen(
                            uiState = viewModel.uiState.collectAsState().value,
                            duration = duration.collectAsState().value,
                            isPaused = isPaused.collectAsState().value,
                            description = description.collectAsState().value,
                            onSettingsTap = {
                                navController.navigate(route = Settings)
                            },
                            onStart = {
                                val intent =
                                    Intent(this@MainActivity, TimerService::class.java).apply {
                                        this.action = "START_TIMER"
                                    }
                                intent.putExtra("time", "demo")
                                launchTimerDateTime = LocalDateTime.now()
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
                            onDescriptionChanged = {
                                _description.value = it
                            },
                            onSelectedCategoryChanged = { category ->
                                viewModel.selectedCategoryChanged(category)
                            }
                        )
                    }
                    composable<Settings> {
                        SettingsScreen(
                            navigateBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
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
        Intent(this, TimerService::class.java).also { intent ->
            bindService(intent, connection, BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        mService!!.durationLiveData.removeObserver(durationObserver)
        mService!!.instantTimerStopLiveData.removeObserver(instantObserver)
        unbindService(connection)
    }
}