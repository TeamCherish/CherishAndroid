package com.sopt.cherish.di

import androidx.lifecycle.ViewModelProvider
import com.sopt.cherish.ui.factory.MainViewModelFactory

/**
 * Created on 01-03 by SSong-develop
 * do not use koin or hilt , just use singleton pattern
 */
object Injection {
    fun provideMainViewModelFactory(): ViewModelProvider.Factory {
        return MainViewModelFactory()
    }
}