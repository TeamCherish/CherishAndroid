package com.sopt.cherish.di

import androidx.lifecycle.ViewModelProvider
import com.sopt.cherish.remote.api.RetrofitBuilder
import com.sopt.cherish.remote.model.UserAPI
import com.sopt.cherish.repository.MainRepository
import com.sopt.cherish.ui.factory.DetailViewModelFactory
import com.sopt.cherish.ui.factory.EnrollmentViewModelFactory
import com.sopt.cherish.ui.factory.MainViewModelFactory

/**
 * Created on 01-03 by SSong-develop
 * do not use koin or hilt , just use singleton pattern
 */
object Injection {

    // Main di
    private fun provideUserAPI(): UserAPI {
        return RetrofitBuilder.userAPI
    }

    private fun provideMainRepository(): MainRepository {
        return MainRepository(provideUserAPI())
    }

    fun provideMainViewModelFactory(): ViewModelProvider.Factory {
        return MainViewModelFactory(provideMainRepository())
    }

    // Detail di
    fun provideDetailViewModelFactory(): ViewModelProvider.Factory {
        return DetailViewModelFactory()
    }

    // enrollment di
    fun provideEnrollmentViewModelFactory(): ViewModelProvider.Factory {
        return EnrollmentViewModelFactory()
    }
}