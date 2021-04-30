package com.sopt.cherish.ui.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.sopt.cherish.R
import com.sopt.cherish.di.Injection
import com.sopt.cherish.util.sendRecallReviewNotification

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        Injection.provideNotificationManager(context).sendRecallReviewNotification(
            context.getString(R.string.notification_recall_review_subtitle),
            context
        )
    }
}