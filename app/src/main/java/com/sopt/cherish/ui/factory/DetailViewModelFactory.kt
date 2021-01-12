package com.sopt.cherish.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sopt.cherish.repository.DetailPlantRepository
import com.sopt.cherish.ui.datail.DetailPlantViewModel

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val detailPlantRepository: DetailPlantRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(DetailPlantViewModel::class.java)) { "unknown class name" }
        return DetailPlantViewModel(detailPlantRepository) as T
    }
}