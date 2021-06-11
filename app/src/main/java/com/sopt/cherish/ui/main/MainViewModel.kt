package com.sopt.cherish.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.cherish.remote.api.*
import com.sopt.cherish.repository.*
import com.sopt.cherish.util.DateUtil
import com.sopt.cherish.util.SimpleLogger
import com.sopt.cherish.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

/**
 * Created by SSong-develop on 2020-12-30
 */

class MainViewModel(
    private val mainRepository: MainRepository,
    private val wateringRepository: WateringRepository,
    private val calendarRepository: CalendarRepository,
    private val notificationRepository: NotificationRepository,
    private val reviewRepository: ReviewRepository
) : ViewModel() {
    val isWatered = SingleLiveEvent<Boolean?>()
    val cherishUserId = MutableLiveData<Int>()
    val userNickName = MutableLiveData<String>()
    val fcmToken = MutableLiveData<String>()
    var cherishSelectedPosition = MutableLiveData<Int>()

    private val _cherishUsers = MutableLiveData<UserResult?>()
    val cherishUsers: MutableLiveData<UserResult?>
        get() = _cherishUsers

    val selectedCherishUser = MutableLiveData<User>()

    var reviewWateringRes = ReviewWateringRes(true, " ", 0)

    val errorHandleLivedata = MutableLiveData<String>()

    init {
        isWatered.value = null
        cherishSelectedPosition.value = 1
    }

    fun fetchUsers() = viewModelScope.launch {
        runCatching {
            mainRepository.fetchCherishUser(cherishUserId.value!!)
        }.onSuccess {
            if (it.userData.totalUser != 0) {
                it.userData.userList.add(0, it.userData.userList[0])
                _cherishUsers.value = it
            } else {
                _cherishUsers.value = null
            }
        }.onFailure { error ->
            errorHandleLivedata.value = error.message
        }
    }

    fun delayFetchUsers() = viewModelScope.launch {
        runCatching {
            mainRepository.fetchCherishUser(cherishUserId.value!!)
        }.onSuccess {
            delay(4000)
            if (it.userData.totalUser != 0) {
                it.userData.userList.add(0, it.userData.userList[0])
                _cherishUsers.value = it
            } else {
                _cherishUsers.value = null
            }
        }.onFailure { error ->
            errorHandleLivedata.value = error.message
        }
    }


    // notification
    fun sendFcmToken(notificationReq: NotificationReq) = viewModelScope.launch {
        runCatching {
            notificationRepository.sendFcmToken(notificationReq)
        }.onSuccess {
            SimpleLogger.logI("send token successful")
        }.onFailure { error ->
            errorHandleLivedata.value = error.message
        }
    }

    fun sendRemindReview(notificationRemindReviewReq: NotificationRemindReviewReq) =
        viewModelScope.launch {
            runCatching {
                notificationRepository.sendRemindReviewNotification(notificationRemindReviewReq)
            }.onSuccess {
                SimpleLogger.logI(it.message)
            }.onFailure { error ->
                errorHandleLivedata.value = error.message
            }
        }

    // Contact Dialog
    private val _calendarData = MutableLiveData<CalendarRes?>()
    val calendarData: MutableLiveData<CalendarRes?>
        get() = _calendarData

    fun fetchCalendarData() = viewModelScope.launch {
        runCatching {
            selectedCherishUser.value?.let { calendarRepository.getChipsData(it.id) }
        }.onSuccess {
            _calendarData.value = it
        }.onFailure { error ->
            errorHandleLivedata.value = error.message
        }
    }

    // [DelayWatering] Server Connection done!
    private val today = DateUtil.convertDateToString(Calendar.getInstance().time)
    private val todayMonth = DateUtil.getMonth(today).toString()
    private val todayDay = DateUtil.getDay(today).toString()

    val delayWateringDateText = "${todayMonth}월${todayDay}일에 물주기"

    // todo : 이녀석 timeOut이 발생하는데 이유를 모르겠음;;;
    // todo : 포스트맨 결과 서버 문제임
    fun postponeWateringDate(postponeWateringDateReq: PostponeWateringDateReq) =
        viewModelScope.launch(Dispatchers.Default) {
            runCatching {
                wateringRepository.postponeWateringDate(postponeWateringDateReq)
            }.onSuccess {
                SimpleLogger.logI(it.message)
                delayFetchUsers()
            }.onFailure { error ->
                errorHandleLivedata.postValue(error.message)
            }
        }

    // Send Review
    fun sendReviewToServer(reviewWateringReq: ReviewWateringReq) = viewModelScope.launch {
        runCatching {
            reviewRepository.sendReviewData(reviewWateringReq)
        }.onSuccess {
            reviewWateringRes = it
        }.onFailure { error ->
            errorHandleLivedata.value = error.message
        }
    }

    // send RemindReview
    fun remindReviewToServer(notificationRemindReviewReq: NotificationRemindReviewReq) =
        viewModelScope.launch {
            runCatching {
                notificationRepository.sendRemindReviewNotification(notificationRemindReviewReq)
            }.onSuccess {
                SimpleLogger.logI(it.message)
            }.onFailure { error ->
                errorHandleLivedata.value = error.message
            }
        }

}