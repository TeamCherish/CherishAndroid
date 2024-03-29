package com.sopt.cherish.ui.review

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.sopt.cherish.di.Injection
import com.sopt.cherish.util.cancelNotification
import com.sopt.cherish.workers.ReviewNotificationWorker

class ReviewNotificationViewModel(
    private val app: Application
) : AndroidViewModel(app) {
    private val reviewNotificationWorker = WorkManager.getInstance(app)
    private val oneTimeJob =
        OneTimeWorkRequest.Builder(ReviewNotificationWorker::class.java).build()
    private val oneTimeJobUid = oneTimeJob.id
    fun startNotificationTimer() {
        Injection.provideNotificationManager(app).cancelNotification()
        reviewNotificationWorker.enqueue(oneTimeJob)
    }

    fun cancel() {
        Injection.provideNotificationManager(app).cancelNotification()
        reviewNotificationWorker.cancelWorkById(oneTimeJobUid)
    }

}