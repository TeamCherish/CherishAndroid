package com.sopt.cherish.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.cherish.remote.api.*
import com.sopt.cherish.repository.CalendarRepository
import com.sopt.cherish.repository.MainRepository
import com.sopt.cherish.repository.NotificationRepository
import com.sopt.cherish.repository.WateringRepository
import com.sopt.cherish.util.DateUtil
import com.sopt.cherish.util.SimpleLogger
import com.sopt.cherish.util.SingleLiveEvent
import kotlinx.coroutines.launch
import java.util.*

/**
 * Created by SSong-develop on 2020-12-30
 */

class MainViewModel(
    private val mainRepository: MainRepository,
    private val wateringRepository: WateringRepository,
    private val calendarRepository: CalendarRepository,
    private val notificationRepository: NotificationRepository
) : ViewModel() {
    val animationTrigger = SingleLiveEvent<Boolean>()
    val cherishUserId = MutableLiveData<Int>()
    val userNickName = MutableLiveData<String>()
    val fcmToken = MutableLiveData<String>()

    private val _cherishUsers = MutableLiveData<UserResult>()
    val cherishUsers: MutableLiveData<UserResult>
        get() = _cherishUsers

    val selectedCherishUser = MutableLiveData<User>()

    fun fetchUsers() = viewModelScope.launch {
        runCatching {
            mainRepository.fetchCherishUser(cherishUserId.value!!)
        }.onSuccess {
            if (it.userData.totalUser != 0) {
                it.userData.userList.add(0, it.userData.userList[0])
                _cherishUsers.value = it
            }
        }.onFailure { error ->
            throw error
        }
    }

    // notification
    fun sendFcmToken(notificationReq: NotificationReq) = viewModelScope.launch {
        runCatching {
            notificationRepository.sendFcmToken(notificationReq)
        }.onSuccess {
            SimpleLogger.logI("send token successful")
        }.onFailure {
            throw it
        }
    }

    fun sendRemindReview(notificationRemindReviewReq: NotificationRemindReviewReq) =
        viewModelScope.launch {
            runCatching {
                notificationRepository.sendRemindReviewNotification(notificationRemindReviewReq)
            }.onSuccess {
                SimpleLogger.logI(it.message)
            }.onFailure {
                throw it
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
        }.onFailure {
            throw it
        }
    }

    // [DelayWatering] Server Connection done!
    private val today = DateUtil.convertDateToString(Calendar.getInstance().time)
    private val todayMonth = DateUtil.getMonth(today).toString()
    private val todayDay = DateUtil.getDay(today).toString()

    val delayWateringDateText = "${todayMonth}월${todayDay}일에 물주기"

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