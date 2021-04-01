package com.sopt.cherish.ui.dialog.plantpopup

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewpager.widget.ViewPager
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentAlertPlantDialogBinding
import com.sopt.cherish.ui.adapter.DialogViewPagerAdapter
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle


class AlertPlantDialogFragment(plantId: Int) : DialogFragment(), View.OnClickListener {

    private lateinit var viewpagerAdapter: DialogViewPagerAdapter
    private var _binding: FragmentAlertPlantDialogBinding? = null
    private val binding get() = _binding!!

    var plantId = plantId


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        //var cherishId:Int=cherishid
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        _binding = FragmentAlertPlantDialogBinding.inflate(inflater, container, false)

        initializeViewPager(binding)
        initializeIndicatorView(binding)

        binding.dialogViewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                binding.indicatorView.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                binding.indicatorView.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        binding.cancelBtn.setOnClickListener {
            dismiss()
        }


        return binding.root

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onClick(view: View?) {
        dismiss()
    }

    private fun initializeViewPager(binding: FragmentAlertPlantDialogBinding) {
        viewpagerAdapter = DialogViewPagerAdapter(childFragmentManager)

        viewpagerAdapter.fragments = listOf(
            PlantDetailPopUpFirst(plantId),
            PlantDetailPopUpSecond(plantId),
            PlantDetailPopUpThird(plantId),
            PlantDetailPopUpFourth(plantId)
        )

        binding.dialogViewpager.adapter = viewpagerAdapter
    }

    private fun initializeIndicatorView(binding: FragmentAlertPlantDialogBinding) {
        binding.indicatorView.apply {
            setSliderColor(Color.parseColor("#c4c4c4"), Color.parseColor("#31d693"))
            setSliderWidth(resources.getDimension(R.dimen.margin_10dp))
            setSliderHeight(resources.getDimension(R.dimen.margin_5dp))
            setSlideMode(IndicatorSlideMode.NORMAL)
            setIndicatorStyle(IndicatorStyle.CIRCLE)
            setPageSize(binding.dialogViewpager.adapter!!.count)
            notifyDataChanged()
        }
    }
}
