package com.sopt.cherish.ui.main.home

import android.animation.ArgbEvaluator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.sopt.cherish.ui.detail.DetailPlantActivity
import com.sopt.cherish.ui.dialog.WateringDialogFragment
import com.sopt.cherish.ui.enrollment.EnrollmentPhoneActivity
import com.sopt.cherish.ui.main.MainViewModel
import com.sopt.cherish.util.PixelUtil.dp


/**
 * 메인 홈뷰
 * 초기상태와 중간에 있는 경우 2개 다 고려해야 합니다.
 * todo : 다시해라 훈기야
 */
class HomeFragment : Fragment(), OnItemClickListener {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var standardBottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var homeCherryListAdapter: HomeCherryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.mainViewModel = viewModel
        homeCherryListAdapter = HomeCherryListAdapter(this) // homeCherryListAdapter 초기화
        // 뷰가 처음 보일 떄
        // 바텀시트 초기화
        initializeBottomSheetBehavior()

        // 바텀시트 리사이클러뷰
        /*setAdapterData(homeCherryListAdapter)*/
        initializeRecyclerView(homeCherryListAdapter)

        // onClick
        binding.homeWateringBtn.setOnClickListener {
            // 물주기 플로우 뭐가 필요한지 생각
            navigateWatering(viewModel.selectedCherishUser.value?.id!!)
        }

        binding.homeUserAddText.setOnClickListener {
            navigatePhoneBook()
        }

        binding.homeMovePlantDetail.setOnClickListener {
            Log.d("homeFragment", viewModel.selectedCherishUser.value?.id!!.toString())
            navigateDetailPlant(viewModel.userId.value!!, viewModel.selectedCherishUser.value?.id!!)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeCherishUsers()
        observeSelectCherishUser()
    }

    private fun observeCherishUsers() {
        viewModel.cherishUsers.observe(viewLifecycleOwner) {
            homeCherryListAdapter.data = it.userData.userList as MutableList<User>
            homeCherryListAdapter.notifyDataSetChanged()
        }
    }

    // 최초 화면이 보여질때
    @SuppressLint("SetTextI18n")
    private fun initializeView(initialUser: User) {
        // 코드 수정 중
        binding.apply {
            // todo : 이미지 관련은 딱 정확하게 정해서 그거대로 처리를 해야할 거 같다.
            // 식물 1,2단계는 사진, 3단계는 gif로 보낼 예정
            // 1,2단계 사진은 배경색이 없고 , 3단계만 배경색이 있다.
            // todo : 데이터 바인딩 제대로 사용해라 훈기야.
            Glide.with(requireContext())
                .load(initialUser.homeMainBackgroundImageUrl)
                .into(homePlantImage)
            homeRemainDate.text = "D${initialUser.dDay}"
            homeSelectedUserStatus.text = initialUser.plantModifier
            homeSelectedUserName.text = initialUser.nickName
            homeAffectionProgressbar.progress = initialUser.growth
            homeAffectionRating.text = "${initialUser.growth}%"
        }
        // todo : 함수화 합시다.
        when {
            initialUser.dDay < 0 -> {
                binding.homeRemainDate.text = "D${initialUser.dDay}"
            }
            initialUser.dDay > 0 -> {
                binding.homeRemainDate.text = "D+${initialUser.dDay}"
            }
            else -> {
                binding.homeRemainDate.text = "D-Day"
            }
        }
        viewModel.selectedCherishUser.value = initialUser
        Log.d("initialView", viewModel.selectedCherishUser.value!!.id.toString())
    }

    private fun initializeBottomSheetBehavior() {
        standardBottomSheetBehavior = BottomSheetBehavior.from(binding.homeStandardBottomSheet)
        // todo : 비율로 변경해야함
        standardBottomSheetBehavior.apply {
            state = BottomSheetBehavior.STATE_COLLAPSED
            peekHeight = 150.dp
            expandedOffset = 100.dp
            halfExpandedRatio = 0.21f
            isHideable = false
        }.also { bottomSheetBehavior ->
            bottomSheetBehavior.addBottomSheetCallback(object :
                BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {

                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    if (standardBottomSheetBehavior.state == BottomSheetBehavior.STATE_DRAGGING && slideOffset < 0.2) {
                        bottomSheetBehavior.peekHeight = 60.dp
                    }
                }
            })
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeSelectCherishUser() {
        viewModel.selectedCherishUser.observe(viewLifecycleOwner) {
            updateProgressBar(it.growth)
            binding.apply {
                Glide.with(requireContext())
                    .load(it.homeMainBackgroundImageUrl)
                    .into(homePlantImage)
                homeSelectedUserName.text = it.nickName
                homeSelectedUserStatus.text = it.plantModifier
                homeAffectionRating.text = "${it.growth}%"
                homeAffectionProgressbar.progress = it.growth
                when {
                    it.dDay < 0 -> {
                        binding.homeRemainDate.text = "D${it.dDay}"
                    }
                    it.dDay > 0 -> {
                        binding.homeRemainDate.text = "D+${it.dDay}"
                    }
                    else -> {
                        binding.homeRemainDate.text = "D-Day"
                    }
                }
            }
        }
    }

    // recyclerview Item click event
    override fun onItemClick(itemBinding: MainCherryItemBinding, position: Int) {
        slideDownBottomSheet()
        viewModel.selectedCherishUser.value = homeCherryListAdapter.data[position]
        Log.d("homeFragment", viewModel.selectedCherishUser.value!!.id.toString())
    }

    private fun initializeRecyclerView(
        homeCherryListAdapter: HomeCherryListAdapter
    ) {
        binding.homeUserList.apply {
            adapter = homeCherryListAdapter
            layoutManager = GridLayoutManager(context, 5)
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
        }
    }



    // navigate
    private fun navigateWatering(id: Int) {
        WateringDialogFragment(id).show(parentFragmentManager, TAG)
    }

    private fun navigatePhoneBook() {
        // phoneBook
        val intent = Intent(context, EnrollmentPhoneActivity::class.java)
        intent.putExtra("userId", viewModel.userId.value)
        startActivity(intent)
    }

    private fun navigateDetailPlant(userId: Int, cherishId: Int) {
        val intent = Intent(activity, DetailPlantActivity::class.java)
        // todo : Parcelable로 변경해서 보내주도록 하자
        intent.putExtra("userId", userId)
        intent.putExtra("cherishId", cherishId)
        intent.putExtra("cherishUserPhoneNumber", viewModel.selectedCherishUser.value?.phoneNumber)
        intent.putExtra("cherishNickname", viewModel.selectedCherishUser.value?.nickName)
        intent.putExtra("userNickname", viewModel.userNickName.value)
        intent.putExtra("userId", viewModel.userId.value)
        startActivity(intent)
    }

    private fun updateProgressBar(rating: Int) {
        if (rating <= 30) {
            binding.homeAffectionProgressbar.progressDrawable = ResourcesCompat.getDrawable(
                resources,
                R.drawable.progress_drawable_verticle_red,
                null
            )
        } else {
            binding.homeAffectionProgressbar.progressDrawable = ResourcesCompat.getDrawable(
                resources,
                R.drawable.progress_drawable_vertical,
                null
            )
        }
    }

    private fun slideDownBottomSheet() {
        // todo : 비율로 변경해야함
        standardBottomSheetBehavior.apply {
            state = BottomSheetBehavior.STATE_COLLAPSED
            peekHeight = 60.dp
            expandedOffset = 100.dp
        }
    }

    // 바텀시트 뒤에 녀석 색상 변경
    private fun transitionBottomSheetParentView(slideOffset: Float) {
        val argbEvaluator =
            ArgbEvaluator().evaluate(slideOffset, R.color.cherish_gray, R.color.black)
        binding.homeFragment.setBackgroundColor(argbEvaluator as Int)
    }

    companion object {
        private val TAG = "HomeFragment"
    }
}

