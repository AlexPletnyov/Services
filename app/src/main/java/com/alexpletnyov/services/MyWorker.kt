package com.alexpletnyov.services

import android.content.Context
import android.util.Log
import androidx.work.*

class MyWorker(
	context: Context,
	private val workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

	override fun doWork(): Result {
		log("doWork")
		val page = workerParameters.inputData.getInt(PAGE, 0)
		for (i in 0..4) {
			Thread.sleep(1000)
			log("timer $i $page")
		}
		return Result.success()
	}

	private fun log(message: String) {
		Log.d("SERVICE_LOG", "MyWorker: $message")
	}

	companion object {

		const val PAGE = "page"
		const val WORK_NAME = "my worker"

		fun makeRequest(page: Int): OneTimeWorkRequest {
			return OneTimeWorkRequestBuilder<MyWorker>()
				.setInputData(workDataOf(PAGE to page))
				.setConstraints(makeConstrains())
				.build()
		}

		private fun makeConstrains() = Constraints.Builder()
			.setRequiresCharging(true)
			.build()
	}
}