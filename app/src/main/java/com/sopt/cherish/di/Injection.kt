package com.sopt.cherish.di

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import com.sopt.cherish.remote.api.*
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.repository.*
import com.sopt.cherish.ui.factory.*
import com.sopt.cherish.util.MyKeyStore

/**
 * Created on 01-03 by SSong-develop
 * do not use koin or hilt , just use singleton pattern
 */
object Injection {

    fun provideMainViewModelFactory(): ViewModelProvider.Factory {
        return MainViewModelFactory(
            provideMainRepository(), provideWateringRepository(), provideCalendarRepository(),
            provideNotificationRepository()
        )
    }

    fun provideHomeBlankViewModelFactory(): ViewModelProvider.Factory {
        return HomeBlankViewModelFactory()
    }

    // User di
    private fun provideUserAPI(): UserAPI {
        return RetrofitBuilder.userAPI
    }

    private fun provideMainRepository(): MainRepository {
        return MainRepository(
            provideUserAPI()
        )
    }

    // Detail di
    private fun provideDetailPlantRepository(): DetailPlantRepository {
        return DetailPlantRepository(provideCalendarAPI(), provideReviewAPI())
    }

    fun provideDetailViewModelFactory(): ViewModelProvider.Factory {
        return DetailViewModelFactory(provideDetailPlantRepository())
    }

    // watering di
    private fun provideWateringRepository(): WateringRepository {
        return WateringRepository(provideWateringAPI())
    }

    private fun provideWateringAPI(): WateringAPI {
        return RetrofitBuilder.wateringAPI
    }

    // review di
    private fun provideReviewRepository(): ReviewRepository {
        return ReviewRepository(provideReviewAPI(), provideNotificationAPI())
    }

    private fun provideReviewAPI(): ReviewAPI {
        return RetrofitBuilder.reviewAPI
    }

    // calendar di
    private fun provideCalendarRepository(): CalendarRepository {
        return CalendarRepository(provideCalendarAPI())
    }

    private fun provideCalendarAPI(): CalendarAPI {
        return RetrofitBuilder.calendarAPI
    }

    // enrollment di
    fun provideEnrollmentViewModelFactory(): ViewModelProvider.Factory {
        return EnrollmentViewModelFactory()
    }

    // review di
    fun provideReviewViewModelFactory(): ViewModelProvider.Factory {
        return ReviewViewModelFactory(provideReviewRepository())
    }

    // notification di
    private fun provideNotificationAPI(): NotificationAPI {
        return RetrofitBuilder.notificationAPI
    }

    private fun provideNotificationRepository(): NotificationRepository {
        return NotificationRepository(provideNotificationAPI())
    }

    // alarm di
    fun provideAlarmDataStore(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            MyKeyStore.provideAlarmDataStoreName(),
            Context.MODE_PRIVATE
        )
    }

}