package com.alexpletnyov.services

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
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
		coroutineScope.launch {
			for (i in 0..100) {
				delay(1000)
				log("timer $i")
			}
			jobFinished(parameters, true)
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
	}
}