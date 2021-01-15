package com.sopt.cherish.ui.datail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.cherish.remote.api.CalendarRes
import com.sopt.cherish.remote.api.ReviewWateringReq
import com.sopt.cherish.repository.DetailPlantRepository
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

    private val _calendarData = MutableLiveData<CalendarRes>()
    val calendarData: MutableLiveData<CalendarRes>
        get() = _calendarData

    fun fetchCalendarData() = viewModelScope.launch {
        _calendarData.postValue(detailPlantRepository.fetchCalendarData(cherishId.value!!))
    }

    var calendarAllowChange = true

    fun sendReviewToServer(reviewWateringReq: ReviewWateringReq) = viewModelScope.launch {
        detailPlantRepository.sendReviewData(reviewWateringReq)
    }

}