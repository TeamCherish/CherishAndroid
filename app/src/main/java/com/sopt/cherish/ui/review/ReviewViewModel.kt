package com.sopt.cherish.ui.review

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.cherish.remote.api.NotificationRemindReviewReq
import com.sopt.cherish.remote.api.ReviewWateringReq
import com.sopt.cherish.remote.api.ReviewWateringRes
import com.sopt.cherish.repository.NotificationRepository
import com.sopt.cherish.repository.ReviewRepository
import com.sopt.cherish.util.SimpleLogger
import kotlinx.coroutines.launch

class ReviewViewModel(
    private val reviewRepository: ReviewRepository,
    private val notificationRepository: NotificationRepository
) : ViewModel() {
    var userNickname = " "
    var selectedCherishNickname = " "
    var selectedCherishId: Int = 0

    var myPageUserNickname = " "
    var myPageUserId: Int = 0

    var reviewText = " "
    var reviewSubText = " "

    var reviewWateringRes = ReviewWateringRes(true, " ", 0)

    val errorHandleLiveData = MutableLiveData<String>()

    fun sendReviewToServer(reviewWateringReq: ReviewWateringReq) = viewModelScope.launch {
        runCatching {
            reviewRepository.sendReviewData(reviewWateringReq)
        }.onSuccess {
            reviewWateringRes = it
        }.onFailure { error ->
            errorHandleLiveData.value = error.message
        }
    }

    fun remindReviewToServer(notificationRemindReviewReq: NotificationRemindReviewReq) =
        viewModelScope.launch {
            runCatching {
                notificationRepository.sendRemindReviewNotification(notificationRemindReviewReq)
            }.onSuccess {
                SimpleLogger.logI(it.message)
            }.onFailure { error ->
                errorHandleLiveData.value = error.message
            }
        }
}