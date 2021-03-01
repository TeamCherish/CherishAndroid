package com.sopt.cherish.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sopt.cherish.repository.CalendarRepository
import com.sopt.cherish.repository.MainRepository
import com.sopt.cherish.repository.NotificationRepository
import com.sopt.cherish.repository.WateringRepository
import com.sopt.cherish.ui.main.MainViewModel

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val mainRepository: MainRepository,
    private val wateringRepository: WateringRepository,
    private val calendarRepository: CalendarRepository,
    private val notificationRepository: NotificationRepository
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(MainViewModel::class.java)) { "unknown class name" }
        return MainViewModel(
            mainRepository,
            wateringRepository,
            calendarRepository,
            notificationRepository
        ) as T
    }
}