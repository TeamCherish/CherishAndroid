package com.sopt.cherish.ui.main.home

import android.animation.ArgbEvaluator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentHomeBinding
import com.sopt.cherish.databinding.MainCherryItemBinding
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
    private lateinit var standardBottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    companion object {
        private val TAG = "HomeFragment"
    }

    // 마이 페이지 userId 값
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.mainViewModel = viewModel

        val homeCherryListAdapter = HomeCherryListAdapter(this)
        initializeBottomSheetBehavior()
        viewModel.fetchUsers()
        binding.homeWateringBtn.setOnClickListener {
            // 물주기 플로우 뭐가 필요한지 생각
            navigateWatering()
        }

        binding.homeUserAddText.setOnClickListener {
            // 바텀시트의 추가하기 버튼 , api 필요 x
            navigatePhoneBook()
        }

        binding.homePlantImage.setOnClickListener {
            // 식물GIF 클릭 시 이동
            navigateDetailPlant(viewModel.userId.value!!, viewModel.cherishUser.value?.id!!)
        }

        setAdapterData(homeCherryListAdapter)
        initializeRecyclerView(homeCherryListAdapter)
        updateProgressBar()
        return binding.root
    }

    private fun navigateDetailPlant(userId: Int, cherishId: Int) {
        val intent = Intent(activity, DetailPlantActivity::class.java)
        intent.putExtra("userId", userId)
        intent.putExtra("cherishId", cherishId)
        startActivity(intent)
    }

    // todo : bottom sheet corner 유지
    // todo : 중간에 걸치는게 1줄짜리로 걸치길 희망
    private fun initializeBottomSheetBehavior() {
        standardBottomSheetBehavior =
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
        viewModel.cherishUsers.observe(viewLifecycleOwner) {
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
        // phoneBook
        val intent = Intent(context, EnrollmentPhoneActivity::class.java)
        intent.putExtra("userId", viewModel.userId.value)
        Log.d("Homefragment", viewModel.userId.value.toString())

        startActivity(intent)
    }

    private fun updateProgressBar() {
        val rating = viewModel.cherishUser.value?.growth?.toInt()
        if (rating != null) {
            if (rating <= 30) {
                binding.homeAffectionProgressbar.progressDrawable = ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.progress_drawable_verticle_red,
                    null
                )
            }
            binding.homeAffectionProgressbar.progress = rating
        }
    }

    // recyclerview Item click event
    // 유저를 클릭할 때 마다 유저 데이터가 viewModel에 변경되어서 observe 되어야 함
    override fun onItemClick(itemBinding: MainCherryItemBinding, position: Int) {
        viewModel.cherishUsers.observe(viewLifecycleOwner) {
            binding.homeCherryNumber.text = it.userData.totalUser.toString()
            it.userData.userList[position].apply {
                initializeViewOnItemClick(this)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initializeViewOnItemClick(user: User) {
        binding.homeSelectedUserName.text = user.nickName
        binding.homeSelectedUserStatus.text = user.plantModifier
        binding.homeAffectionRating.text = user.growth.toString()
        binding.homeAffectionProgressbar.progress = user.growth
        when {
            user.dDay < 0 -> {
                binding.homeRemainDate.text = "D${user.dDay}"
            }
            user.dDay > 0 -> {
                binding.homeRemainDate.text = "D+${user.dDay}"
            }
            else -> {
                binding.homeRemainDate.text = "D-Day"
            }
        }
        // animationUrl 데이터 갱신해달라고 해야함
        Glide.with(requireContext())
            .load(user.plantAnimationUrl)
            .into(binding.homePlantImage)
    }

    private fun transitionBottomSheetParentView(slideOffset: Float) {
        val argbEvaluator =
            ArgbEvaluator().evaluate(slideOffset, R.color.white, R.color.black)
        binding.homeFragment.setBackgroundColor(argbEvaluator as Int)
    }
}