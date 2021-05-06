package com.sopt.cherish.ui.notification

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.sopt.cherish.R
import com.sopt.cherish.di.Injection
import com.sopt.cherish.ui.signin.SignInActivity
import com.sopt.cherish.util.MyKeyStore
import com.sopt.cherish.util.SimpleLogger
import com.sopt.cherish.util.extension.ContextExtension.getIntent

class FirebaseNotificationService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        SimpleLogger.logI(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val intent = this.getIntent<SignInActivity>()
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val notificationManager = Injection.provideNotificationManager(this)
        val notificationId = MyKeyStore.provideNeedToWateringNotificationId()

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_ONE_SHOT)
        val notification = NotificationCompat.Builder(
            this,
            this.getString(R.string.notification_cherish_need_to_watering_user_channel_id)
        )
            .setContentTitle(message.notification?.title)
            .setContentText(message.notification?.body)
            .setSmallIcon(R.drawable.login_logo)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}