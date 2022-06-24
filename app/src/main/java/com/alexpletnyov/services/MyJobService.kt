package com.alexpletnyov.services

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.media.browse.MediaBrowser.EXTRA_PAGE
import android.os.Build
import android.util.Log
import kotlinx.coroutines.*

class MyJobService : JobService() {

	private val coroutineScope = CoroutineScope(Dispatchers.Main)

	override fun onCreate() {
		super.onCreate()
		log("onCreate")
	}

	override fun onStartJob(parameters: JobParameters?): Boolean {
		log("onStartJob")
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			coroutineScope.launch {
				var workItem = parameters?.dequeueWork()
				while (workItem != null) {
					val page = workItem.intent.getIntExtra(EXTRA_PAGE, 0)

					for (i in 0..5) {
						delay(1000)
						log("timer $i $page")
					}

					parameters?.completeWork(workItem)
					workItem = parameters?.dequeueWork()
				}
				jobFinished(parameters, false)
			}
		}
		return true
	}

	override fun onStopJob(p0: JobParameters?): Boolean {
		log("onStopJob")
		return true
	}

	override fun onDestroy() {
		super.onDestroy()
		log("onDestroy")
		coroutineScope.cancel()
	}

	fun log(message: String) {
		Log.d("SERVICE_LOG", "MyJobService: $message")
	}

	companion object {

		const val JOB_ID = 11
		private const val EXTRA_PAGE = "page"

		fun newIntent(context: Context, page: Int): Intent {
			return Intent(context, MyJobService::class.java).apply {
				putExtra(EXTRA_PAGE, page)
			}
		}
	}
}