package com.alexpletnyov.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class MyService : Service() {

	private val coroutineScope = CoroutineScope(Dispatchers.Main)

	override fun onCreate() {
		super.onCreate()
		log("onCreate")
	}

	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
		log("onStartCommand")
		val start = intent?.getIntExtra(EXTRA_NUMBER_OF_START, 0) ?: 0
		coroutineScope.launch {
			for (i in start..start + 100) {
				delay(1000)
				log("timer $i")
			}
		}

		return START_REDELIVER_INTENT
	}

	override fun onDestroy() {
		super.onDestroy()
		log("onDestroy")
		coroutineScope.cancel()
	}

	fun log(message: String) {
		Log.d("SERVICE_LOG", "MyService: $message")
	}

	override fun onBind(intent: Intent?): IBinder? {
		TODO("Not yet implemented")
	}

	companion object {

		private const val EXTRA_NUMBER_OF_START = "number"
		fun newIntent(context: Context, startFrom: Int): Intent {
			return Intent(context, MyService::class.java).apply {
				putExtra(EXTRA_NUMBER_OF_START, startFrom)
			}
		}
	}
}