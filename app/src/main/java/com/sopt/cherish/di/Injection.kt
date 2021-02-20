package com.sopt.cherish.di

import androidx.lifecycle.ViewModelProvider
import com.sopt.cherish.remote.api.CalendarAPI
import com.sopt.cherish.remote.api.ReviewAPI
import com.sopt.cherish.remote.api.UserAPI
import com.sopt.cherish.remote.api.WateringAPI
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.repository.*
import com.sopt.cherish.ui.factory.*

/**
 * Created on 01-03 by SSong-develop
 * do not use koin or hilt , just use singleton pattern
 */
object Injection {

    fun provideMainViewModelFactory(): ViewModelProvider.Factory {
        return MainViewModelFactory(
            provideMainRepository(), provideWateringRepository(),
            provideReviewRepository(), provideCalendarRepository()
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
        return ReviewRepository(provideReviewAPI())
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

}