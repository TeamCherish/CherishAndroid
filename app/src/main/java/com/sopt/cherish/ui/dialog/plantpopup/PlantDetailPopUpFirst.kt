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
import com.sopt.cherish.databinding.FragmentPlantDetailPopUpFirstBinding
import com.sopt.cherish.remote.api.PlantDetailData
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.util.PixelUtil.dp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PlantDetailPopUpFirst(plantId: Int) : Fragment() {

    private var _binding: FragmentPlantDetailPopUpFirstBinding? = null
    private val binding get() = _binding!!
    private val requestData = RetrofitBuilder
    var plantId = plantId
    var flowerName:String=""
    var chip:String=""
    var wateringText:String=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPlantDetailPopUpFirstBinding.inflate(inflater, container, false)

        setMargin()
        initializeServerRequest(binding)

        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun initializeServerRequest(binding: FragmentPlantDetailPopUpFirstBinding) {

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

                                Log.d("image url", it.data.toString())
                                flowerName = it.data.plantResponse[0].modifier
                                Glide.with(binding.root.context)
                                    .load(it.data.plantResponse[0].image_url)
                                    .into(binding.flowerImg)
                                chip = "꽃말 | " + it.data.plantResponse[0].flower_meaning
                                wateringText = it.data.plantResponse[0].explanation

                                binding.flowerName.text=flowerName
                                binding.chip.text=chip
                                binding.wateringText.text=wateringText

                            }
                    }
                })
    }

    private fun setMargin(){
            when(plantId){
                1 -> setRosmary()
                2 -> setAmericanBlue()
                3 -> setDandelion()
                4 -> setCactus()
                5 -> setStookie()
            }
    }

    private fun setDandelion(){
        var param=binding.flowerImg.layoutParams as ViewGroup.MarginLayoutParams
        binding.flowerImg.layoutParams.width=95.dp
        binding.flowerImg.layoutParams.height=177.dp

        var margin=23.dp
        param.setMargins(0,margin,0,0)
        binding.flowerImg.layoutParams=param

        param=binding.chip.layoutParams as ViewGroup.MarginLayoutParams
        margin=9.dp
        param.setMargins(0, margin, 0, 0)
        binding.chip.layoutParams=param
        binding.chip.setTextColor(Color.parseColor("#97cdbd"))
        binding.chip.setChipStrokeColorResource(R.color.cherish_dandelion_background_color)

        param=binding.wateringText.layoutParams as ViewGroup.MarginLayoutParams
        margin=8.dp
        param.setMargins(0, margin, 0, 0)
        binding.wateringText.layoutParams=param
    }

    private fun setCactus(){
        var param=binding.flowerImg.layoutParams as ViewGroup.MarginLayoutParams
        binding.flowerImg.layoutParams.width=129.dp
        binding.flowerImg.layoutParams.height=170.dp

        var margin=28.dp
        param.setMargins(0,margin,0,0)
        binding.flowerImg.layoutParams=param

        param=binding.chip.layoutParams as ViewGroup.MarginLayoutParams
        margin=11.dp
        param.setMargins(0, margin, 0, 0)
        binding.chip.layoutParams=param
        binding.chip.setTextColor(Color.parseColor("#9AB7DE"))
        binding.chip.setChipStrokeColorResource(R.color.plantid4)

        param=binding.wateringText.layoutParams as ViewGroup.MarginLayoutParams
        margin=8.dp
        param.setMargins(0, margin, 0, 0)
        binding.wateringText.layoutParams=param
    }

    private fun setAmericanBlue(){
        var param=binding.flowerImg.layoutParams as ViewGroup.MarginLayoutParams
        binding.flowerImg.layoutParams.width=67.dp
        binding.flowerImg.layoutParams.height=168.dp

        var margin=20.dp
        param.setMargins(0,margin,0,0)
        binding.flowerImg.layoutParams=param

        param=binding.chip.layoutParams as ViewGroup.MarginLayoutParams
        margin=21.dp
        param.setMargins(0, margin, 0, 0)
        binding.chip.layoutParams=param
        binding.chip.setTextColor(Color.parseColor("#8f95af"))
        binding.chip.setChipStrokeColorResource(R.color.cherish_american_blue_background_color)

        param=binding.wateringText.layoutParams as ViewGroup.MarginLayoutParams
        margin=8.dp
        param.setMargins(0, margin, 0, 0)
        binding.wateringText.layoutParams=param
    }

    private fun setRosmary(){
        var param=binding.flowerImg.layoutParams as ViewGroup.MarginLayoutParams
        binding.flowerImg.layoutParams.width=61.dp
        binding.flowerImg.layoutParams.height=168.dp

        var margin=35.dp
        param.setMargins(0,margin,0,0)
        binding.flowerImg.layoutParams=param

        param=binding.chip.layoutParams as ViewGroup.MarginLayoutParams
        margin=6.dp
        param.setMargins(0, margin, 0, 0)
        binding.chip.layoutParams=param
        binding.chip.setTextColor(Color.parseColor("#f1b0bc"))
        binding.chip.setChipStrokeColorResource(R.color.cherish_rosemary_background_color)

        param=binding.wateringText.layoutParams as ViewGroup.MarginLayoutParams
        margin=8.dp
        param.setMargins(0, margin, 0, 0)
        binding.wateringText.layoutParams=param
    }

    private fun setStookie(){
        var param=binding.flowerImg.layoutParams as ViewGroup.MarginLayoutParams
        binding.flowerImg.layoutParams.width=119.dp
        binding.flowerImg.layoutParams.height=170.dp

        var margin=20.dp
        param.setMargins(0,margin,0,0)
        binding.flowerImg.layoutParams=param

        param=binding.chip.layoutParams as ViewGroup.MarginLayoutParams
        margin=19.dp
        param.setMargins(0, margin, 0, 0)
        binding.chip.layoutParams=param
        binding.chip.setTextColor(Color.parseColor("#9ab7de"))
        binding.chip.setChipStrokeColorResource(R.color.cherish_stuki_background_color)

        param=binding.wateringText.layoutParams as ViewGroup.MarginLayoutParams
        margin=8.dp
        param.setMargins(0, margin, 0, 0)
        binding.wateringText.layoutParams=param
    }
}