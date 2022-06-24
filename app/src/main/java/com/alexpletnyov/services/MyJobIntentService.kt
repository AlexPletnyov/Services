package com.alexpletnyov.services

import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.JobIntentService
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*

class MyJobIntentService : JobIntentService() {

	override fun onCreate() {
		super.onCreate()
		log("onCreate")
	}

	override fun onHandleWork(intent: Intent) {
		log("onHandleWork")
		val page = intent.getIntExtra(EXTRA_PAGE, 0)
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
		Log.d("SERVICE_LOG", "MyJobIntentService: $message")
	}

	companion object {

		private const val EXTRA_PAGE = "page"
		private const val JOB_ID = 111

		fun enqueue(context: Context, page: Int) {
			enqueueWork(
				context,
				MyJobIntentService::class.java,
				JOB_ID,
				newIntent(context, page)
			)
		}

		private fun newIntent(context: Context, page: Int): Intent {
			return Intent(context, MyJobIntentService::class.java).apply {
				putExtra(EXTRA_PAGE, page)
			}
		}
	}
}