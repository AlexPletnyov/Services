package com.alexpletnyov.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.alexpletnyov.services.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

	private val binding by lazy {
		ActivityMainBinding.inflate(layoutInflater)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		binding.simpleService.setOnClickListener {
			startService(MyService.newIntent(this, 25))
			stopService(MyForegroundService.newIntent(this))
		}

		binding.foregroundService.setOnClickListener {
			ContextCompat.startForegroundService(
				this,
				MyForegroundService.newIntent(this)
			)
		}

		binding.intentService.setOnClickListener {
			ContextCompat.startForegroundService(
				this,
				MyIntentService.newIntent(this)
			)
		}

		binding.jobScheduler.setOnClickListener {
			val componentName = ComponentName(this, MyJobService::class.java)

			val jobInfo = JobInfo.Builder(MyJobService.JOB_ID, componentName)
				.setRequiresCharging(true)
				.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
				.setPersisted(true)
				.build()

			val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
			jobScheduler.schedule(jobInfo)
		}
	}
}