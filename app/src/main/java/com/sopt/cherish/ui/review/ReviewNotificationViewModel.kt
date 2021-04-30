package com.sopt.cherish.ui.review

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.cherish.R
import com.sopt.cherish.di.Injection
import com.sopt.cherish.util.cancelNotification
import com.sopt.cherish.util.sendRecallReviewNotification
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ReviewNotificationViewModel(
    private val app: Application
) : AndroidViewModel(app) {

    fun startNotificationTimer() {
        Injection.provideNotificationManager(app).cancelNotification()
        viewModelScope.launch {
            delay(15000)
            Injection.provideNotificationManager(app).sendRecallReviewNotification(
                app.getString(R.string.notification_recall_review_subtitle),
                app
            )
        }
    }

    fun cancel() {
        Injection.provideNotificationManager(app).cancelNotification()
    }

}