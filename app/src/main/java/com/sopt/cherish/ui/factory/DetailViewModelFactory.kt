package com.sopt.cherish.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sopt.cherish.repository.DetailPlantRepository
import com.sopt.cherish.repository.WateringRepository
import com.sopt.cherish.ui.detail.DetailPlantViewModel

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(
    private val detailPlantRepository: DetailPlantRepository,
    private val wateringRepository: WateringRepository
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(DetailPlantViewModel::class.java)) { "unknown class name" }
        return DetailPlantViewModel(detailPlantRepository, wateringRepository) as T
    }
}