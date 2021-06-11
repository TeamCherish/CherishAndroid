package com.sopt.cherish.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sopt.cherish.repository.NotificationRepository
import com.sopt.cherish.repository.ReviewRepository
import com.sopt.cherish.ui.review.ReviewViewModel

@Suppress("UNCHECKED_CAST")
class ReviewViewModelFactory(
    private val reviewRepository: ReviewRepository,
    private val notificationRepository: NotificationRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(ReviewViewModel::class.java)) { "unknown class name" }
        return ReviewViewModel(reviewRepository, notificationRepository) as T
    }
}