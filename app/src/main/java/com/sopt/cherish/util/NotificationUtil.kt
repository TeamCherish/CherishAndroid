package com.sopt.cherish.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.content.Context
import android.os.Build
import com.sopt.cherish.R

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
        val recallReviewChannelImportance = IMPORTANCE_DEFAULT
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

}