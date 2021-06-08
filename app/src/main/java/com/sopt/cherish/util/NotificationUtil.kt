package com.sopt.cherish.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.sopt.cherish.R
import com.sopt.cherish.ui.receiver.AlarmReceiver
import com.sopt.cherish.util.extension.ContextExtension.getIntent

fun NotificationManager.createNeedToWateringUser(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val needToWateringChannelName =
            context.getString(R.string.notification_cherish_need_to_watering_user_channel_name)
        val needToWateringChannelDescription =
            context.getString(R.string.notification_cherish_need_to_watering_user_channel_description)
        val needToWateringChannelImportance = IMPORTANCE_HIGH
        val needToWateringChannel = NotificationChannel(
            context.getString(R.string.notification_cherish_need_to_watering_user_channel_id),
            needToWateringChannelName,
            needToWateringChannelImportance
        )
        needToWateringChannel.description = needToWateringChannelDescription
        createNotificationChannel(needToWateringChannel)
    }
}

fun NotificationManager.createRecallReview(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val recallReviewChannelName =
            context.getString(R.string.notification_cherish_recall_review_channel_name)
        val recallReviewChannelDescription =
            context.getString(R.string.notification_cherish_recall_review_channel_description)
        val recallReviewChannelImportance = IMPORTANCE_HIGH
        val recallReviewChannel = NotificationChannel(
            context.getString(R.string.notification_cherish_recall_review_channel_id),
            recallReviewChannelName,
            recallReviewChannelImportance
        )
        recallReviewChannel.description = recallReviewChannelDescription
        createNotificationChannel(recallReviewChannel)
    }
}

fun NotificationManager.sendRecallReviewNotification(messageBody: String, context: Context) {
    val contentIntent = context.getIntent<AlarmReceiver>()
    contentIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    val contentPendingIntent = PendingIntent.getBroadcast(
        context,
        MyKeyStore.provideRecallReviewNotificationId(),
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    val notificationBuilder = NotificationCompat.Builder(
        context,
        context.getString(R.string.notification_cherish_recall_review_channel_id)
    )
        .setContentTitle(context.getString(R.string.notification_recall_review_title))
        .setContentText(messageBody)
        .setSmallIcon(R.drawable.login_logo)
        .setAutoCancel(true)
        .setContentIntent(contentPendingIntent)

    notify(MyKeyStore.provideRecallReviewNotificationId(), notificationBuilder.build())
}

fun NotificationManager.sendWateringNotification(meesageBody: String, context: Context) {

}

fun NotificationManager.cancelNotification() {
    cancelAll()
}