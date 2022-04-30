package com.example.myapplication.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {

    private val channelID = "com.example.myapplication.notification.channel1"
    private var notificationManager: NotificationManager? = null

    private lateinit var binding: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel(channelID, "DemoChannel", "this is a demo")

        binding.button.setOnClickListener {
            displayNotification()
        }
    }

    private fun displayNotification() {
        val notificationId = 45
        NotificationCompat.Builder(this@NotificationActivity, channelID).apply {
            setContentTitle("Demo Title")
            setContentText("This is a notification demo A")
            setAutoCancel(true)
            setSmallIcon(androidx.appcompat.R.drawable.abc_btn_radio_material)
            priority = NotificationCompat.PRIORITY_HIGH
        }.build().let {
            notificationManager?.notify(notificationId, it)
        }
    }

    private fun createNotificationChannel(id: String, name: String, channelDescription: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, importance).apply {
                description = channelDescription
            }
            notificationManager?.createNotificationChannel(channel)
        } else {
            displayNotification()
        }
    }
}