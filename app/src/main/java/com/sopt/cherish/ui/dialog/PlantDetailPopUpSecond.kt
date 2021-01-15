package com.sopt.cherish.ui.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.sopt.cherish.databinding.FragmentPlantDetailPopUpSecondBinding
import com.sopt.cherish.remote.api.PlantDetailData
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PlantDetailPopUpSecond(cherishId :Int) : Fragment() {

    private var _binding: FragmentPlantDetailPopUpSecondBinding? = null
    private val binding get() = _binding!!
    private val requestData = RetrofitBuilder
    var cherishId=cherishId

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPlantDetailPopUpSecondBinding.inflate(inflater, container, false)

        initializeServerRequest(binding)

        return binding.root


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initializeServerRequest(binding: FragmentPlantDetailPopUpSecondBinding) {

        requestData.plantDetailAPI.fetchUserPage(cherishId)
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
                                    .load(it.data.plantDetail[0].image_url).into(binding.flowerImg)
                                binding.chip.text = it.data.plantDetail[0].level_name
                                binding.wateringText.text = it.data.plantDetail[0].description

                            }
                    }
                })
    }
}