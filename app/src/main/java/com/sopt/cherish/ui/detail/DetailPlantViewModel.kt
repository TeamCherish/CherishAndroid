package com.sopt.cherish.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.sopt.cherish.remote.api.*
import com.sopt.cherish.repository.DetailPlantRepository
import com.sopt.cherish.util.SingleLiveEvent
import kotlinx.coroutines.launch

class DetailPlantViewModel(
    private val detailPlantRepository: DetailPlantRepository
) : ViewModel() {
    var calendarModeChangeEvent = SingleLiveEvent<Boolean>()

    val animationTrigger = SingleLiveEvent<Boolean>()

    val cherishId = MutableLiveData<Int>()

    val cherishPhoneNumber = MutableLiveData<String>()

    val cherishNickname = MutableLiveData<String>()

    val userNickname = MutableLiveData<String>()

    val userId = MutableLiveData<Int>()

    val selectedCalendarData = MutableLiveData<CalendarData?>()

    val selectedCalendarDay = MutableLiveData<CalendarDay?>()

    var wateringText = " "

    private val _calendarData = MutableLiveData<CalendarRes>()
    val calendarData: MutableLiveData<CalendarRes>
        get() = _calendarData

    fun fetchCalendarData() = viewModelScope.launch {
        runCatching {
            detailPlantRepository.fetchCalendarData(cherishId.value!!)
        }.onSuccess {
            _calendarData.value = it
        }.onFailure { error ->
            throw error
        }
    }

    // [Review]
    fun sendReviewToServer(reviewWateringReq: ReviewWateringReq) = viewModelScope.launch {
        runCatching {
            detailPlantRepository.sendReviewData(reviewWateringReq)
        }.onSuccess {
            // Toast를 띄우든 뭐든 해보는게?
        }.onFailure { error ->
            throw error
        }
    }

    // [Revise Review]
    fun sendReviseReviewToServer(reviseReviewReq: ReviseReviewReq) = viewModelScope.launch {
        runCatching {
            detailPlantRepository.sendReviseReviewData(reviseReviewReq)
        }.onSuccess {
            // Toast를 띄우는건?
        }.onFailure { error ->
            throw error
        }
    }

    // [Delete Review]
    fun deleteReview(deleteReviewReq: DeleteReviewReq) = viewModelScope.launch {
        runCatching {
            detailPlantRepository.deleteReviewData(deleteReviewReq)
        }.onSuccess {
            // Toast
        }.onFailure { error ->
            throw error
        }
    }

}