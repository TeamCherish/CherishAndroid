package com.sopt.cherish.ui.main.home

import android.animation.ArgbEvaluator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentHomeBinding
import com.sopt.cherish.ui.adapter.HomeCherryListAdapter
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

    private val viewModel: MainViewModel by activityViewModels()

    companion object {
        private val TAG = "HomeFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.mainViewModel = viewModel
        val homeCherryListAdapter = HomeCherryListAdapter()

        initializeBottomSheetBehavior(binding)

        binding.homeWateringBtn.setOnClickListener {
            navigateWatering()
        }

        binding.homeUserAddText.setOnClickListener {
            navigatePhoneBook()
        }

        setAdapterData(homeCherryListAdapter)
        initializeRecyclerView(binding, homeCherryListAdapter)

        updateProgressBar(binding)
        return binding.root
    }

    private fun initializeBottomSheetBehavior(binding: FragmentHomeBinding) {
        val standardBottomSheetBehavior =
            BottomSheetBehavior.from(binding.homeStandardBottomSheet)
        // bottom sheet state 지정
        standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        standardBottomSheetBehavior.peekHeight = 160
        standardBottomSheetBehavior.expandedOffset = 158
        standardBottomSheetBehavior.isHideable = false

        standardBottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // 이 코드 때문에 색상이 변경 안되는거 같은데 여기 로직 다시 한번 생각
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
                // 이건 작동 잘함
                transitionBottomSheetParentView(binding, slideOffset)
            }
        })
    }

    private fun setAdapterData(homeCherryListAdapter: HomeCherryListAdapter) {
        homeCherryListAdapter.data = viewModel.dummyCherry
        homeCherryListAdapter.notifyDataSetChanged()
    }

    private fun initializeRecyclerView(
        binding: FragmentHomeBinding,
        homeCherryListAdapter: HomeCherryListAdapter
    ) {
        binding.homeUserList.apply {
            adapter = homeCherryListAdapter
            layoutManager = GridLayoutManager(context, 5)
        }
    }

    private fun navigateWatering() {
        WateringDialogFragment().show(parentFragmentManager, TAG)
    }

    private fun navigatePhoneBook() {
        val intent = Intent(context, PhoneBookActivity::class.java)
        startActivity(intent)
    }

    private fun transitionBottomSheetParentView(binding: FragmentHomeBinding, slideOffset: Float) {
        val argbEvaluator =
            ArgbEvaluator().evaluate(slideOffset, R.color.white, R.color.black)
        binding.homeFragment.setBackgroundColor(argbEvaluator as Int)
    }

    private fun updateProgressBar(binding: FragmentHomeBinding) {
        // 이거 고쳐야 함
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