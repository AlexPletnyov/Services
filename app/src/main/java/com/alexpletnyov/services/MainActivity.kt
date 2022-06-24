package com.alexpletnyov.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
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
	}
}