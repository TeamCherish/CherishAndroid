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
import com.sopt.cherish.remote.api.User
import com.sopt.cherish.ui.adapter.HomeCherryListAdapter
import com.sopt.cherish.ui.adapter.OnItemClickListener
import com.sopt.cherish.ui.datail.DetailPlantActivity
import com.sopt.cherish.ui.dialog.WateringDialogFragment
import com.sopt.cherish.ui.enrollment.EnrollmentPhoneActivity
import com.sopt.cherish.ui.main.MainViewModel
import com.sopt.cherish.util.PixelUtil.dp


/**
 * 메인 홈뷰
 * 초기상태와 중간에 있는 경우 2개 다 고려해야 합니다.
 * 여기는 어떻게 하면 더 이쁘게 나올까를 생각하면 될거 같습니다.
 * need it!!!
 */
class HomeFragment : Fragment(), OnItemClickListener {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentHomeBinding

    companion object {
        private val TAG = "HomeFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.mainViewModel = viewModel
        initializeView()
        val homeCherryListAdapter = HomeCherryListAdapter(this)

        initializeBottomSheetBehavior()

        binding.homeWateringBtn.setOnClickListener {
            navigateWatering()
        }

        binding.homeUserAddText.setOnClickListener {
            navigatePhoneBook()
        }

        binding.homeDummyFlowerImage.setOnClickListener {
            navigateDetailPlant()
        }

        setAdapterData(homeCherryListAdapter)
        initializeRecyclerView(homeCherryListAdapter)

        updateProgressBar()
        return binding.root
    }

    private fun initializeView() {
        // 나의 소중한 사람들 count
        viewModel.users.observe(viewLifecycleOwner) {
            binding.homeCherryNumber.text = it.userData.totalUser.toString()
        }
    }

    private fun navigateDetailPlant() {
        val intent = Intent(requireContext(), DetailPlantActivity::class.java)
        startActivity(intent)
    }

    private fun initializeBottomSheetBehavior() {
        val standardBottomSheetBehavior =
            BottomSheetBehavior.from(binding.homeStandardBottomSheet)
        // bottom sheet state 지정
        standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        standardBottomSheetBehavior.peekHeight = 60.dp
        standardBottomSheetBehavior.expandedOffset = 50.dp
        standardBottomSheetBehavior.isHideable = false
        binding.homeFragment.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.cherish_purple
            )
        )
        standardBottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                /* transitionBottomSheetParentView(slideOffset)*/
            }
        })
    }

    private fun setAdapterData(homeCherryListAdapter: HomeCherryListAdapter) {
        viewModel.fetchUsers()
        viewModel.users.observe(viewLifecycleOwner) {
            homeCherryListAdapter.data = it.userData.userList as MutableList<User>
            homeCherryListAdapter.notifyDataSetChanged()
        }
    }

    private fun initializeRecyclerView(
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
        val intent = Intent(context, EnrollmentPhoneActivity::class.java)
        startActivity(intent)
    }

    private fun transitionBottomSheetParentView(slideOffset: Float) {
        val argbEvaluator =
            ArgbEvaluator().evaluate(slideOffset, R.color.white, R.color.black)
        binding.homeFragment.setBackgroundColor(argbEvaluator as Int)
    }

    private fun updateProgressBar() {
        val rating = binding.homeAffectionProgressbar.progress
        if (rating <= 30) {
            binding.homeAffectionProgressbar.progressDrawable = ResourcesCompat.getDrawable(
                resources,
                R.drawable.progress_drawable_verticle_red,
                null
            )
        }
    }

    // recyclerview Item click event
    override fun onItemClick(user: User) {
        if (user.dDay > 21) {
            binding.homeFragment.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.cherish_stuki_background_color
                )
            )
            binding.homeDummyFlowerImage.setImageResource(R.drawable.main_img_stuki)
        } else {
            binding.homeFragment.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.cherish_sun_background_color
                )
            )
            binding.homeDummyFlowerImage.setImageResource(R.drawable.main_img_sun)
        }
    }
}