package com.sopt.cherish.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.cherish.remote.api.*
import com.sopt.cherish.repository.DetailPlantRepository
import com.sopt.cherish.util.DateUtil
import com.sopt.cherish.util.SingleLiveEvent
import kotlinx.coroutines.launch

class DetailPlantViewModel(
    private val detailPlantRepository: DetailPlantRepository
) : ViewModel() {

    // Keyword , Review 조회하기
    val cherishId = MutableLiveData<Int>()

    val userStatus1 = MutableLiveData<String>()
    val userStatus2 = MutableLiveData<String>()
    val userStatus3 = MutableLiveData<String>()

    val cherishPhoneNumber = MutableLiveData<String>()

    val cherishNickname = MutableLiveData<String>()

    val userNickname = MutableLiveData<String>()

    val userId = MutableLiveData<Int>()

    // ReviseReview를 위한 변수들
    // calendarData 는 물준날 , 리뷰 , 리뷰 1,2,3 총 5개로 이루어져있습니다.
    val selectedCalendarData = MutableLiveData<CalendarData>()

    val selectedCalendarDate = selectedCalendarData.value?.wateredDate?.let {
        DateUtil.convertDateToCalendarDay(
            it
        )
    }

    val selectedDate = selectedCalendarDate

    private val _calendarData = MutableLiveData<CalendarRes>()
    val calendarData: MutableLiveData<CalendarRes>
        get() = _calendarData

    fun fetchCalendarData() = viewModelScope.launch {
        _calendarData.postValue(detailPlantRepository.fetchCalendarData(cherishId.value!!))
    }

    var calendarModeChangeEvent = SingleLiveEvent<Boolean>()

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