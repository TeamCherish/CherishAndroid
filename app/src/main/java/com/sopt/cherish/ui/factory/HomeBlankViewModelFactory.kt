package com.sopt.cherish.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sopt.cherish.ui.main.HomeBlankViewModel

@Suppress("UNCHECKED_CAST")
class HomeBlankViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(HomeBlankViewModel::class.java)) { "unknown class name" }
        return HomeBlankViewModel() as T
    }
}