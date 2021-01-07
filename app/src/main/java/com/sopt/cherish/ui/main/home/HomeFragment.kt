package com.sopt.cherish.ui.main.home

import android.animation.ArgbEvaluator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentHomeBinding
import com.sopt.cherish.ui.adapter.MainBottomSheetAdapter
import com.sopt.cherish.ui.dialog.WateringDialogFragment
import com.sopt.cherish.ui.enrollment.PhoneBookActivity
import com.sopt.cherish.ui.main.MainViewModel


/**
 * 메인 홈뷰
 * 초기상태와 중간에 있는 경우 2개 다 고려해야 합니다.
 * 여기는 어떻게 하면 더 이쁘게 나올까를 생각하면 될거 같습니다.
 * need it!!!
 */
class HomeFragment : Fragment() {

    private lateinit var standardBottomSheetBehavior: BottomSheetBehavior<*>
    private lateinit var adapter: MainBottomSheetAdapter

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        navigateWatering(binding)

        adapter = MainBottomSheetAdapter()
        setAdapterData(adapter)
        setAdapter(binding, adapter)

        binding.homeBottomSheet.homeUserAddBtn.setOnClickListener {
            navigatePhoneBook()
        }

        initializeBottomSheetBehavior(binding)
        initializeRecyclerView(binding.homeFragment, adapter)
        updateProgressBar(binding)

        return binding.root
    }

    private fun initializeBottomSheetBehavior(binding: FragmentHomeBinding) {
        standardBottomSheetBehavior =
            BottomSheetBehavior.from(binding.homeBottomSheet.homeStandardBottomSheet)
        standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        standardBottomSheetBehavior.peekHeight = 160
        standardBottomSheetBehavior.expandedOffset = 158
        standardBottomSheetBehavior.isHideable = false

        standardBottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    binding.homeFragment.setBackgroundColor(
                        ContextCompat.getColor(
                            context!!,
                            R.color.cherish_purple
                        )
                    )
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                transitionBottomSheetParentView(binding, slideOffset)
            }
        })
    }

    private fun initializeRecyclerView(
        standardBottomSheet: ConstraintLayout?,
        mainAdapter: MainBottomSheetAdapter
    ) {
        val recyclerView = standardBottomSheet?.findViewById<RecyclerView>(R.id.home_user_list)
        recyclerView?.apply {
            adapter = mainAdapter
            layoutManager = GridLayoutManager(context, 5)
        }
    }

    private fun navigateWatering(binding: FragmentHomeBinding) {
        binding.homeWateringBtn.setOnClickListener {
            WateringDialogFragment().show(parentFragmentManager, "HomeFragment")
        }
    }

    private fun navigatePhoneBook() {
        val intent = Intent(context, PhoneBookActivity::class.java)
        startActivity(intent)
    }

    private fun transitionBottomSheetParentView(binding: FragmentHomeBinding, slideOffset: Float) {
        val argbEvaluator =
            ArgbEvaluator().evaluate(slideOffset, R.color.cherish_purple, R.color.cherish_black)
        binding.homeFragment.setBackgroundColor(argbEvaluator as Int)
    }

    private fun setAdapterData(adapter: MainBottomSheetAdapter) {
        adapter.data = viewModel.dummyCherry
        adapter.notifyDataSetChanged()
    }

    private fun setAdapter(binding: FragmentHomeBinding, mainAdapter: MainBottomSheetAdapter) {
        binding.homeBottomSheet.homeUserList.apply {
            adapter = mainAdapter
            layoutManager = GridLayoutManager(context, 5)
        }
    }

    private fun updateProgressBar(binding: FragmentHomeBinding) {
        val rating = binding.homeAffectionProgressbar.progress
        if (rating <= 30) {
            binding.homeAffectionProgressbar.progressDrawable = ResourcesCompat.getDrawable(
                resources,
                R.drawable.progress_drawable_verticle_red,
                null
            )
        }
    }
}