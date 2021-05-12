package com.sopt.cherish.ui.review

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.cherish.remote.api.ReviewWateringReq
import com.sopt.cherish.remote.api.ReviewWateringRes
import com.sopt.cherish.repository.ReviewRepository
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
}