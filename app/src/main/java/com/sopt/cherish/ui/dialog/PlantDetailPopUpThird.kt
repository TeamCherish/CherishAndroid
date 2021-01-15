package com.sopt.cherish.ui.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.sopt.cherish.databinding.FragmentPlantDetailPopUpThirdBinding
import com.sopt.cherish.remote.api.PlantDetailData
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlantDetailPopUpThird(plantId: Int) : Fragment() {


    private var _binding: FragmentPlantDetailPopUpThirdBinding? = null
    private val binding get() = _binding!!
    private val requestData = RetrofitBuilder
    var plantId = plantId

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPlantDetailPopUpThirdBinding.inflate(inflater, container, false)

        initializeServerRequest(binding)
        return binding.root
    }

    private fun initializeServerRequest(binding: FragmentPlantDetailPopUpThirdBinding) {

        requestData.plantDetailAPI.fetchUserPage(plantId)
            .enqueue(
                object : Callback<PlantDetailData> {
                    override fun onFailure(call: Call<PlantDetailData>, t: Throwable) {
                        Log.d("통신 실패", t.toString())
                    }

                    override fun onResponse(
                        call: Call<PlantDetailData>,
                        response: Response<PlantDetailData>
                    ) {
                        Log.d("success", response.body().toString())
                        response.takeIf {
                            it.isSuccessful
                        }?.body()
                            ?.let { it ->

                                Log.d("data success!", it.data.toString())


                                Glide.with(binding.root.context)
                                    .load(it.data.plantDetail[1].image_url).into(binding.flowerImg)
                                binding.chip.text = it.data.plantDetail[1].level_name
                                binding.wateringText.text = it.data.plantDetail[1].description

                            }
                    }
                })
    }

}