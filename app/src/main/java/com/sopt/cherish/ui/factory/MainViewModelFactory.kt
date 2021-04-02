package com.sopt.cherish.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sopt.cherish.repository.*
import com.sopt.cherish.ui.main.MainViewModel

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val mainRepository: MainRepository,
    private val wateringRepository: WateringRepository,
    private val calendarRepository: CalendarRepository,
    private val notificationRepository: NotificationRepository,
    private val reviewRepository: ReviewRepository
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(MainViewModel::class.java)) { "unknown class name" }
        return MainViewModel(
            mainRepository,
            wateringRepository,
            calendarRepository,
            notificationRepository,
            reviewRepository
        ) as T
    }
}