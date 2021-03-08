package com.sopt.cherish.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.sopt.cherish.remote.api.*
import com.sopt.cherish.repository.DetailPlantRepository
import com.sopt.cherish.repository.WateringRepository
import com.sopt.cherish.util.DateUtil
import com.sopt.cherish.util.SimpleLogger
import com.sopt.cherish.util.SingleLiveEvent
import kotlinx.coroutines.launch
import java.util.*

class DetailPlantViewModel(
    private val detailPlantRepository: DetailPlantRepository,
    private val wateringRepository: WateringRepository
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

    var selectedUserDday = 0

    var wateringText = " "

    var dDay = 0

    val selectedMemoCalendarDay = MutableLiveData<CalendarDay>()

    private val today = DateUtil.convertDateToString(Calendar.getInstance().time)
    private val todayMonth = DateUtil.getMonth(today).toString()
    private val todayDay = DateUtil.getDay(today).toString()

    val delayWateringDateText = "${todayMonth}월${todayDay}일에 물주기"

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

    // [Delay Watering]
    fun postponeWateringDate(postponeWateringDateReq: PostponeWateringDateReq) =
        viewModelScope.launch {
            runCatching {
                wateringRepository.postponeWateringDate(postponeWateringDateReq)
            }.onSuccess {
                SimpleLogger.logI(it.message)
            }.onFailure {
                throw it
            }
        }

}