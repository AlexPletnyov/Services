package com.alexpletnyov.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*

class MyForegroundService : Service() {

	private val coroutineScope = CoroutineScope(Dispatchers.Main)

	override fun onCreate() {
		super.onCreate()
		log("onCreate")
		createNotificationChannel()
		startForeground(NOTIFICATION_ID, createNotification())
	}

	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
		log("onStartCommand")
		coroutineScope.launch {
			for (i in 0..100) {
				delay(1000)
				log("timer $i")
			}
			stopSelf()
		}
		return START_STICKY
	}

	override fun onDestroy() {
		super.onDestroy()
		log("onDestroy")
		coroutineScope.cancel()
	}

	private fun log(message: String) {
		Log.d("SERVICE_LOG","MyForegroundService: $message")
	}

	private fun createNotificationChannel() {
		val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			val notificationChannel = NotificationChannel(
				CHANNEL_ID,
				CHANNEL_NAME,
				NotificationManager.IMPORTANCE_DEFAULT
			)
			notificationManager.createNotificationChannel(notificationChannel)
		}
	}

	private fun createNotification() = NotificationCompat.Builder(this, CHANNEL_ID)
		.setContentTitle("Foreground service")
		.setContentText("Foreground service is starting")
		.setSmallIcon(R.drawable.ic_launcher_background)
		.build()

	override fun onBind(intent: Intent?): IBinder? {
		TODO("Not yet implemented")
	}

	companion object {

		private const val CHANNEL_ID = "channel_id"
		private const val CHANNEL_NAME = "channel_name"
		private const val NOTIFICATION_ID = 1

		fun newIntent(context: Context): Intent {
			return Intent(context, MyForegroundService::class.java)
		}
	}
}