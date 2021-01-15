package com.sopt.cherish.ui.dialog


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.sopt.cherish.databinding.FragmentPlantDetailPopUpFirstBinding
import com.sopt.cherish.remote.api.PlantDetailData
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PlantDetailPopUpFirst : Fragment() {

    private var _binding: FragmentPlantDetailPopUpFirstBinding? = null
    private val binding get() = _binding!!
    private val requestData = RetrofitBuilder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPlantDetailPopUpFirstBinding.inflate(inflater, container, false)

        initializeServerRequest(binding)

        return binding.root

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initializeServerRequest(binding: FragmentPlantDetailPopUpFirstBinding) {

        requestData.plantDetailAPI.fetchUserPage(1)
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

                                binding.flowerName.text = it.data.plantResponse[0].modifier
                                Glide.with(binding.root.context)
                                    .load(it.data.plantResponse[0].image_url)
                                    .into(binding.flowerImg)
                                binding.chip.text =
                                    "꽃말 | " + it.data.plantResponse[0].flower_meaning
                                binding.wateringText.text = it.data.plantResponse[0].explanation

                            }
                    }
                })
    }

}