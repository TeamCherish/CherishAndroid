package com.sopt.cherish.ui.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.cherish.remote.api.NotificationWateringReq
import com.sopt.cherish.remote.api.ReviewWateringReq
import com.sopt.cherish.remote.api.ReviewWateringRes
import com.sopt.cherish.repository.ReviewRepository
import com.sopt.cherish.util.SimpleLogger
import kotlinx.coroutines.launch

class ReviewViewModel(
    private val reviewRepository: ReviewRepository
) : ViewModel() {
    var userNickname = " "
    var selectedCherishNickname = " "
    var selectedCherishId: Int = 0

    var reviewText = " "
    var reviewSubText = " "

    var reviewWateringRes = ReviewWateringRes(true, " ", 0)

    fun sendReviewToServer(reviewWateringReq: ReviewWateringReq) = viewModelScope.launch {
        runCatching {
            reviewRepository.sendReviewData(reviewWateringReq)
        }.onSuccess {
            reviewWateringRes = it
        }.onFailure {
            throw it
        }
    }

    // 이거 헷갈림 뭐지????
    // COM 과 REV의 차이를 뭔가 알아야 할거 같은데;;;;
    fun sendWateringNotification(notificationWateringReq: NotificationWateringReq) =
        viewModelScope.launch {
            runCatching {
                reviewRepository.sendWateringNotification(notificationWateringReq)
            }.onSuccess {
                SimpleLogger.logI(it.success.toString())
            }.onFailure {
                throw it
            }
        }
}