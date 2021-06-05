package com.sopt.cherish.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sopt.cherish.R
import com.sopt.cherish.di.Injection
import com.sopt.cherish.util.sendRecallReviewNotification
import kotlinx.coroutines.delay

class ReviewNotificationWorker(
    private val context: Context,
    private val params: WorkerParameters
) : CoroutineWorker(context, params) {
    private val second: Long = 1000L
    private val minute: Long = second * 1000L
    override suspend fun doWork(): Result {
        return try {
            delay(5 * minute)
            val notificationManager = Injection.provideNotificationManager(context)
            notificationManager.sendRecallReviewNotification(
                context.getString(R.string.notification_recall_review_subtitle),
                context
            )
            Result.success()
        } catch (exception: Exception) {
            Result.failure()
        }
    }

}