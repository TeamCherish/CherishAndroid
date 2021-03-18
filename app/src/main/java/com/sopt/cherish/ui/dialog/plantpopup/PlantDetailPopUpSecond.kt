package com.sopt.cherish.ui.dialog.plantpopup

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentPlantDetailPopUpSecondBinding
import com.sopt.cherish.remote.api.PlantDetailData
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PlantDetailPopUpSecond(plantId: Int) : Fragment() {

    private var _binding: FragmentPlantDetailPopUpSecondBinding? = null
    private val binding get() = _binding!!
    private val requestData = RetrofitBuilder
    var plantId = plantId
    var flowerName: String = ""
    var chip: String = ""
    var wateringText: String = ""

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

                        response.takeIf {
                            it.isSuccessful
                        }?.body()
                            ?.let { it ->

                                Glide.with(binding.root.context)
                                    .load(it.data.plantDetail[0].image_url)
                                    .into(binding.flowerImg)
                                chip = it.data.plantDetail[0].level_name
                                wateringText = it.data.plantDetail[0].description


                                binding.chip.text = chip
                                binding.wateringText.text = wateringText

                                setMargin(flowerName, chip, wateringText)
                            }
                    }
                })
    }

    private fun setMargin(flowerName: String, chip: String, wateringText: String) {
        when (flowerName) {
            "따뜻한 민들레" -> setDandelion()
            "씩씩한 단모환" -> setCactus()
            "푸른 빛의 아메리칸 블루" -> setAmericanBlue()
            "향기로운 로즈마리" -> setRosmary()
            "든든한 스투키" -> setStookie()
        }
    }

    private fun setDandelion() {
        var param = binding.flowerImg.layoutParams as ViewGroup.MarginLayoutParams
        param.setMargins(0, 70, 0, 0)
        binding.flowerImg.layoutParams = param

        param = binding.chip.layoutParams as ViewGroup.MarginLayoutParams
        param.setMargins(0, 9, 0, 0)
        binding.chip.layoutParams = param
        binding.chip.setTextColor(Color.parseColor("#97cdbd"))
        binding.chip.setChipStrokeColorResource(R.color.cherish_dandelion_background_color)

        param = binding.wateringText.layoutParams as ViewGroup.MarginLayoutParams
        param.setMargins(0, 8, 0, 0)
        binding.wateringText.layoutParams = param
    }

    private fun setCactus() {

    }

    private fun setAmericanBlue() {

    }

    private fun setRosmary() {

    }

    private fun setStookie() {

    }
}