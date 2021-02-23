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
    // Keyword , Review 조회하기
    val cherishId = MutableLiveData<Int>()

    val cherishPhoneNumber = MutableLiveData<String>()

    val cherishNickname = MutableLiveData<String>()

    val userNickname = MutableLiveData<String>()

    val userId = MutableLiveData<Int>()

    val selectedCalendarData = MutableLiveData<CalendarData?>()

    val selectedCalendarDay = MutableLiveData<CalendarDay?>()

    private val _calendarData = MutableLiveData<CalendarRes>()
    val calendarData: MutableLiveData<CalendarRes>
        get() = _calendarData

    var calendarModeChangeEvent = SingleLiveEvent<Boolean>()

    val animationTrigger = SingleLiveEvent<Boolean>()

    fun fetchCalendarData() = viewModelScope.launch {
        _calendarData.postValue(detailPlantRepository.fetchCalendarData(cherishId.value!!))
    }

    // [Review]
    fun sendReviewToServer(reviewWateringReq: ReviewWateringReq) = viewModelScope.launch {
        detailPlantRepository.sendReviewData(reviewWateringReq)
    }

    // [Revise Review]
    fun sendReviseReviewToServer(reviseReviewReq: ReviseReviewReq) = viewModelScope.launch {
        detailPlantRepository.sendReviseReviewData(reviseReviewReq)
    }

    // [Delete Review]
    fun deleteReview(deleteReviewReq: DeleteReviewReq) = viewModelScope.launch {
        detailPlantRepository.deleteReviewData(deleteReviewReq)
    }

}