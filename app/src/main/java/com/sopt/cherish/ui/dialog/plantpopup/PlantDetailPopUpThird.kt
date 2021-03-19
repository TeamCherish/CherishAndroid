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
import com.sopt.cherish.databinding.FragmentPlantDetailPopUpThirdBinding
import com.sopt.cherish.remote.api.PlantDetailData
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.util.PixelUtil.dp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlantDetailPopUpThird(plantId: Int) : Fragment() {


    private var _binding: FragmentPlantDetailPopUpThirdBinding? = null
    private val binding get() = _binding!!
    private val requestData = RetrofitBuilder
    var plantId = plantId
    var chip: String = ""
    var wateringText: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPlantDetailPopUpThirdBinding.inflate(inflater, container, false)

        setMargin()
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

                        response.takeIf {
                            it.isSuccessful
                        }?.body()
                            ?.let { it ->

                                Glide.with(binding.root.context)
                                    .load(it.data.plantDetail[1].image_url).into(binding.flowerImg)
                                chip = it.data.plantDetail[1].level_name
                                wateringText = it.data.plantDetail[1].description
                                binding.chip.text = chip
                                binding.wateringText.text = wateringText

                            }
                    }
                })
    }

    private fun setMargin() {
        when (plantId) {
            1 -> setRosmary()
            2 -> setAmericanBlue()
            3 -> setDandelion()
            4 -> setCactus()
            5 -> setStookie()
        }
    }

    private fun setDandelion() {
        var param = binding.flowerImg.layoutParams as ViewGroup.MarginLayoutParams
        binding.flowerImg.layoutParams.width = 84.dp
        binding.flowerImg.layoutParams.height = 139.dp
        var margin = 60.dp
        param.setMargins(0, margin, 0, 0)
        binding.flowerImg.layoutParams = param

        param = binding.chip.layoutParams as ViewGroup.MarginLayoutParams
        margin = 7.dp
        param.setMargins(0, margin, 0, 0)
        binding.chip.layoutParams = param
        binding.chip.setTextColor(Color.parseColor("#97cdbd"))
        binding.chip.setChipStrokeColorResource(R.color.cherish_dandelion_background_color)

        param = binding.wateringText.layoutParams as ViewGroup.MarginLayoutParams
        margin = 8.dp
        param.setMargins(0, margin, 0, 0)
        binding.wateringText.layoutParams = param
    }

    private fun setCactus() {
        var param = binding.flowerImg.layoutParams as ViewGroup.MarginLayoutParams
        binding.flowerImg.layoutParams.width = 117.dp
        binding.flowerImg.layoutParams.height = 144.dp

        var margin = 54.dp
        param.setMargins(0, margin, 0, 0)
        binding.flowerImg.layoutParams = param

        param = binding.chip.layoutParams as ViewGroup.MarginLayoutParams
        margin = 8.dp
        param.setMargins(0, margin, 0, 0)
        binding.chip.layoutParams = param
        binding.chip.setTextColor(Color.parseColor("#9AB7DE"))
        binding.chip.setChipStrokeColorResource(R.color.plantid4)

        param = binding.wateringText.layoutParams as ViewGroup.MarginLayoutParams
        margin = 8.dp
        param.setMargins(0, margin, 0, 0)
        binding.wateringText.layoutParams = param
    }

    private fun setAmericanBlue() {
        var param = binding.flowerImg.layoutParams as ViewGroup.MarginLayoutParams
        binding.flowerImg.layoutParams.width = 67.dp
        binding.flowerImg.layoutParams.height = 152.dp

        var margin = 34.dp
        param.setMargins(0, margin, 0, 0)
        binding.flowerImg.layoutParams = param

        param = binding.chip.layoutParams as ViewGroup.MarginLayoutParams
        margin = 20.dp
        param.setMargins(0, margin, 0, 0)
        binding.chip.layoutParams = param
        binding.chip.setTextColor(Color.parseColor("#8f95af"))
        binding.chip.setChipStrokeColorResource(R.color.cherish_american_blue_background_color)

        param = binding.wateringText.layoutParams as ViewGroup.MarginLayoutParams
        margin = 8.dp
        param.setMargins(0, margin, 0, 0)
        binding.wateringText.layoutParams = param
    }

    private fun setRosmary() {
        var param = binding.flowerImg.layoutParams as ViewGroup.MarginLayoutParams
        binding.flowerImg.layoutParams.width = 56.dp
        binding.flowerImg.layoutParams.height = 156.dp

        var margin = 37.dp
        param.setMargins(0, margin, 0, 0)
        binding.flowerImg.layoutParams = param

        param = binding.chip.layoutParams as ViewGroup.MarginLayoutParams
        margin = 13.dp
        param.setMargins(0, margin, 0, 0)
        binding.chip.layoutParams = param
        binding.chip.setTextColor(Color.parseColor("#f1b0bc"))
        binding.chip.setChipStrokeColorResource(R.color.cherish_rosemary_background_color)

        param = binding.wateringText.layoutParams as ViewGroup.MarginLayoutParams
        margin = 8.dp
        param.setMargins(0, margin, 0, 0)
        binding.wateringText.layoutParams = param
    }

    private fun setStookie() {
        var param = binding.flowerImg.layoutParams as ViewGroup.MarginLayoutParams
        binding.flowerImg.layoutParams.width = 138.dp
        binding.flowerImg.layoutParams.height = 144.dp

        var margin = 38.dp
        param.setMargins(0, margin, 0, 0)
        binding.flowerImg.layoutParams = param

        param = binding.chip.layoutParams as ViewGroup.MarginLayoutParams
        margin = 24.dp
        param.setMargins(0, margin, 0, 0)
        binding.chip.layoutParams = param
        binding.chip.setTextColor(Color.parseColor("#9ab7de"))
        binding.chip.setChipStrokeColorResource(R.color.cherish_stuki_background_color)

        param = binding.wateringText.layoutParams as ViewGroup.MarginLayoutParams
        margin = 8.dp
        param.setMargins(0, margin, 0, 0)
        binding.wateringText.layoutParams = param
    }
}