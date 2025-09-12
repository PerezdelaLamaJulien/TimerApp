package com.jperez.timerapp

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Binder
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.text.format.DateUtils
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import java.time.Duration
import java.time.Instant

class
TimerService : Service() {
    val TAG = "TimerService"
    private val CHANNEL_ID = "foreground_timer_channel" // Define your channel ID
    private val handler = Handler(Looper.getMainLooper()) // Handler to update every second

    var duration : Duration = Duration.ZERO
    val durationLiveData = MutableLiveData<Duration>()

    val instantTimerStopLiveData = MutableLiveData<Instant?>()

    private val updateTimeRunnable = object : Runnable {
        override fun run() {
            // Increase the time by 1 second
            duration = duration.plusSeconds(1)
            durationLiveData.postValue(duration)
            // Update the notification with the new time
            updateNotification(duration)
            // Re-run this task every second (1000 ms)
            handler.postDelayed(this, 1000)
        }
    }

    // Flags for controlling the timer
    private var isPaused = false

    // Binder given to clients.
    private val binder = LocalBinder()

    /**
     * Class used for the client Binder. Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    inner class LocalBinder : Binder() {
        // Return this instance of TimerService so clients can call public methods.
        fun getService(): TimerService = this@TimerService
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        val time = intent?.getStringExtra("time")
        Log.d(TAG, "onStartCommand: $time")

        when (intent?.action) {
            "START_TIMER" -> startTimer()
            "PAUSE_TIMER" -> pauseTimer()
            "STOP_TIMER" -> stopTimer()
        }

        return START_STICKY
    }

    @SuppressLint("ForegroundServiceType")
    override fun onCreate() {
        super.onCreate()

        // Create the notification channel if running on Android Oreo (API 26) or higher
        val name = "Foreground Timer Service"
        val descriptionText = "Channel for Foreground Timer Service"
        val importance = NotificationManager.IMPORTANCE_MIN
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun updateNotification(duration: Duration) {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_MUTABLE)

        // Create a new notification with the updated time
        //DateUtils.formatElapsedTime(1)
        val temp = DateUtils.formatElapsedTime(duration.seconds)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Timer")
            .setContentText(temp)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .addAction(R.drawable.ic_launcher_foreground, "Pause", getPausePendingIntent())  // Pause button
            .addAction(R.drawable.ic_launcher_foreground, "Stop", getStopPendingIntent())  // Stop button
            .setContentIntent(pendingIntent)
            .build()

        // Update the foreground service notification
        startForeground(1, notification)
    }

    // Get PendingIntent for pause
    private fun getPausePendingIntent(): PendingIntent {
        val pauseIntent = Intent(this, TimerService::class.java).apply {
            this.action = "PAUSE_TIMER"
        }
        return PendingIntent.getService(this, 0, pauseIntent, PendingIntent.FLAG_MUTABLE)
    }

    // Get PendingIntent for stop
    private fun getStopPendingIntent(): PendingIntent {
        val stopIntent = Intent(this, TimerService::class.java).apply {
            this.action = "STOP_TIMER"
        }
        return PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_MUTABLE)
    }

    private fun startNotificationService() {
        // Initial notification with pause and stop buttons
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Timer")
            .setContentText("00:00")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .addAction(R.drawable.ic_launcher_background, "Pause", getPausePendingIntent()) // Pause button
            .addAction(R.drawable.ic_launcher_background, "Stop", getStopPendingIntent())  // Stop button
            .setContentIntent(PendingIntent.getActivity(this, 0, Intent(this, MainActivity::class.java),
                PendingIntent.FLAG_MUTABLE))
            .build()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            startForeground(1, notification)
            Log.d(TAG, "onCreate: Running on older Android version")
        } else {
            Log.d(TAG, "onCreate: Running on Android Tiramisu or newer")
            startForeground(1, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC)
        }
    }

    fun startTimer(){
        handler.post(updateTimeRunnable)
        isPaused = false
        instantTimerStopLiveData.postValue(null)
        startNotificationService()
    }

    private fun pauseTimer() {
        if (isPaused){
            isPaused = false
            handler.post(updateTimeRunnable)
            return
        }
        isPaused = true
        handler.removeCallbacks(updateTimeRunnable)  // Stop updating the timer
    }

    private fun stopTimer() {
        instantTimerStopLiveData.postValue(Instant.now())
        stopSelf()  // Stop the service and remove notification
        stopForeground(STOP_FOREGROUND_REMOVE)
        handler.removeCallbacks(updateTimeRunnable)  // Stop updating the timer
        duration = Duration.ZERO
        durationLiveData.postValue(duration)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop the handler from running when the service is destroyed
        handler.removeCallbacks(updateTimeRunnable)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }
}