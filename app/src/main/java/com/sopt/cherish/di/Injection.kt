package com.sopt.cherish.di

import androidx.lifecycle.ViewModelProvider
import com.sopt.cherish.ui.factory.DetailViewModelFactory
import com.sopt.cherish.ui.factory.EnrollmentViewModelFactory
import com.sopt.cherish.ui.factory.MainViewModelFactory

/**
 * Created on 01-03 by SSong-develop
 * do not use koin or hilt , just use singleton pattern
 */
object Injection {
    fun provideMainViewModelFactory(): ViewModelProvider.Factory {
        return MainViewModelFactory()
    }

    fun provideDetailViewModelFactory(): ViewModelProvider.Factory {
        return DetailViewModelFactory()
    }

    fun provideEnrollmentViewModelFactory(): ViewModelProvider.Factory {
        return EnrollmentViewModelFactory()
    }
}