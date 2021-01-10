package com.sopt.cherish.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sopt.cherish.ui.enrollment.EnrollmentViewModel

@Suppress("UNCHECKED_CAST")
class EnrollmentViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(EnrollmentViewModel::class.java)) { "unknown class name" }
        return EnrollmentViewModel() as T
    }
}