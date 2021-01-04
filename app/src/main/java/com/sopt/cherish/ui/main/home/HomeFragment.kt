package com.sopt.cherish.ui.main.home

import android.animation.ArgbEvaluator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentHomeBinding
import com.sopt.cherish.ui.dialog.WateringDialogFragment


/**
 * 메인 홈뷰
 * 초기상태와 중간에 있는 경우 2개 다 고려해야 합니다.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var standardBottomSheetBehavior: BottomSheetBehavior<*>
    private lateinit var standardBottomSheet: ConstraintLayout
    private lateinit var mViewBg: ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        // conflict 날 수 있는 자리
        binding.buttonWater.setOnClickListener {
            WateringDialogFragment().show(parentFragmentManager, "HomeFragment")
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewBg = binding.homeFragmentBg

        standardBottomSheet = view.findViewById(R.id.standardBottomSheet)

        standardBottomSheetBehavior = BottomSheetBehavior.from(standardBottomSheet)
        standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        standardBottomSheetBehavior.peekHeight = 160
        standardBottomSheetBehavior.expandedOffset = 158


        standardBottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {

                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    mViewBg.setBackgroundColor(
                        ContextCompat.getColor(
                            context!!,
                            R.color.cherish_purple
                        )
                    )
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                //transitionBottomSheetParentView(slideOffset)
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun transitionBottomSheetParentView(slideOffset: Float) {

        val argbEvaluator = ArgbEvaluator().evaluate(slideOffset, 0x8189b3, 0x242222)
        mViewBg.setBackgroundColor(argbEvaluator as Int)

    }
}