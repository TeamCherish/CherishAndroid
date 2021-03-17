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
    var chip:String=""
    var wateringText:String=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

                        response.takeIf {
                            it.isSuccessful
                        }?.body()
                            ?.let { it ->
                                Log.d("image url",it.data.toString())
                                Glide.with(binding.root.context)
                                    .load(it.data.plantDetail[1].image_url).into(binding.flowerImg)
                                chip=it.data.plantDetail[1].level_name
                                wateringText=it.data.plantDetail[1].description
                                binding.chip.text = chip
                                binding.wateringText.text = wateringText

                                setMargin()
                            }
                    }
                })
    }
    private fun setMargin(){
        when(plantId){
            1->setRosmary()
            2->setAmericanBlue()
            3->setDandelion()
            4->setCactus()
            5->setStookie()
        }
    }

    private fun setDandelion(){
        var param=binding.flowerImg.layoutParams as ViewGroup.MarginLayoutParams
        param.setMargins(0,60,0,0)
        param.width=84.dp
        param.height=139.dp
        binding.flowerImg.layoutParams=param

        param=binding.chip.layoutParams as ViewGroup.MarginLayoutParams
        param.setMargins(0,7,0,0)
        binding.chip.layoutParams=param
        binding.chip.setTextColor(Color.parseColor("#97cdbd"))
        binding.chip.setChipStrokeColorResource(R.color.cherish_dandelion_background_color)

        param=binding.wateringText.layoutParams as ViewGroup.MarginLayoutParams
        param.setMargins(0,8,0,0)
        binding.wateringText.layoutParams=param
    }

    private fun setCactus(){

    }

    private fun setAmericanBlue(){

    }

    private fun setRosmary(){

    }

    private fun setStookie(){

    }
}