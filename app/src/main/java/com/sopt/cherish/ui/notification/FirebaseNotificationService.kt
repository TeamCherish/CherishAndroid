package com.sopt.cherish.ui.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.sopt.cherish.R
import com.sopt.cherish.ui.notification.FirebaseNotificationService.Companion.CHANNEL_ID
import com.sopt.cherish.ui.signin.SignInActivity
import kotlin.random.Random

class FirebaseNotificationService : FirebaseMessagingService() {

    companion object {
        const val CHANNEL_ID = "cherish_channel"
    }

    override fun onNewToken(token: String) {
        Log.d("Refreshed Token", token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val intent = Intent(this, SignInActivity::class.java)
        intent.putExtra("needToWWateringCherishId", message.data["CherishId"]?.toInt())

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = Random.nextInt()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_ONE_SHOT)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(message.notification?.title)
            .setContentText(message.notification?.body)
            .setSmallIcon(R.drawable.login_logo)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}

private fun createNotificationChannel(notificationManager: NotificationManager) {
    val channelName = "CherishNotificationChannel"
    val channel = NotificationChannel(CHANNEL_ID, channelName, IMPORTANCE_HIGH).apply {
        description = "Cherish channel description"
        enableLights(true)
        lightColor = Color.GREEN
    }
    notificationManager.createNotificationChannel(channel)
}