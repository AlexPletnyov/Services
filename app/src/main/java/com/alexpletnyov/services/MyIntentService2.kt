package com.alexpletnyov.services

import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*

class MyIntentService2 : IntentService(NAME) {

	override fun onCreate() {
		super.onCreate()
		log("onCreate")
		setIntentRedelivery(true)
	}

	override fun onHandleIntent(intent: Intent?) {
		log("onHandleIntent")
		val page = intent?.getIntExtra(EXTRA_PAGE, 0) ?: 0
		for (i in 0..4) {
			Thread.sleep(1000)
			log("timer $i $page")
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		log("onDestroy")
	}

	private fun log(message: String) {
		Log.d("SERVICE_LOG", "MyIntentService2: $message")
	}

	companion object {

		private const val EXTRA_PAGE = "page"
		private const val NAME = "name2"

		fun newIntent(context: Context, page: Int): Intent {
			return Intent(context, MyIntentService2::class.java).apply {
				putExtra(EXTRA_PAGE, page)
			}
		}
	}
}