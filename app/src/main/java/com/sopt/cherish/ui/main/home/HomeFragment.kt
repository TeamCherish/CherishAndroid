package com.sopt.cherish.ui.main.home

import android.animation.ArgbEvaluator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
        viewModel.fetchUsers()
        homeCherryListAdapter = HomeCherryListAdapter(this)
        observeSelectCherishUser()

        // 뷰가 처음 보일 떄
        // 바텀시트 초기화
        initializeBottomSheetBehavior()

        // 바텀시트 리사이클러뷰
        setAdapterData(homeCherryListAdapter)
        initializeRecyclerView(homeCherryListAdapter)

        // 물주기 혹은 미루기를 했을 때 애니메이션의 변화
        observeWateringOrDelayAnimation()

        // onClick
        binding.homeWateringBtn.setOnClickListener {
            // 물주기 플로우 뭐가 필요한지 생각
            navigateWatering(viewModel.selectedCherishUser.value?.id!!)
        }

        binding.homeUserAddText.setOnClickListener {
            navigatePhoneBook()
        }

        binding.homeMovePlantDetail.setOnClickListener {
            navigateDetailPlant(viewModel.userId.value!!, viewModel.selectedCherishUser.value?.id!!)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchUsers()
        homeCherryListAdapter.notifyDataSetChanged()
        // 이게 추가한 코드인데 이거 없이 되는 지 테스트 한번 해보자
        setAdapterData(homeCherryListAdapter)
        initializeRecyclerView(homeCherryListAdapter)
        // todo : 만약 클릭한다음에 온다면 기존에 클릭했던 사람을 보여주는게 맞다고 생각 함
    }

    // 최초 화면이 보여질때
    @SuppressLint("SetTextI18n")
    private fun initializeView(initialUser: User) {
        // 코드 수정 중
        binding.apply {
            // todo : 이미지 관련은 딱 정확하게 정해서 그거대로 처리를 해야할 거 같다.
            // 식물 1,2단계는 사진, 3단계는 gif로 보낼 예정
            // 1,2단계 사진은 배경색이 없고 , 3단계만 배경색이 있다.
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
        viewModel.cherishUsers.observe(viewLifecycleOwner) {
            viewModel.selectedCherishUser.value = it.userData.userList[0]
            binding.homeCherryNumber.text = it.userData.totalUser.toString()
        }
    }

    private fun initializeBottomSheetBehavior() {
        standardBottomSheetBehavior = BottomSheetBehavior.from(binding.homeStandardBottomSheet)
        // todo : 비율로 변경해야함
        standardBottomSheetBehavior.apply {
            state = BottomSheetBehavior.STATE_COLLAPSED
            peekHeight = 160.dp
            expandedOffset = 100.dp
            halfExpandedRatio = 0.23f
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
        viewModel.cherishUsers.observe(viewLifecycleOwner) {
            viewModel.selectedCherishUser.value = it.userData.userList[position]
        }
    }

    private fun initializeRecyclerView(
        homeCherryListAdapter: HomeCherryListAdapter
    ) {
        binding.homeUserList.apply {
            adapter = homeCherryListAdapter
            layoutManager = GridLayoutManager(context, 5)
            isNestedScrollingEnabled = false
        }
    }

    private fun setAdapterData(homeCherryListAdapter: HomeCherryListAdapter) {
        viewModel.cherishUsers.observe(viewLifecycleOwner) {
            homeCherryListAdapter.data = it.userData.userList as MutableList<User>
            initializeView(it.userData.userList[0])
            homeCherryListAdapter.notifyDataSetChanged()
        }
    }

    private fun observeWateringOrDelayAnimation() {
        viewModel.animationTrigger.observe(viewLifecycleOwner) { isWateringOrDelaying ->
            // todo : 이거 고쳐야함
            if (isWateringOrDelaying) {
                Handler(Looper.getMainLooper()).postDelayed({ // Runnble 객체와 time을 파라미터로 받는다
                    Glide.with(binding.homePlantImage)
                        .asGif()
                        .load(R.raw.watering_min_android)
                        .into(binding.homePlantImage)
                }, 2000)
            } else {
                Handler(Looper.getMainLooper()).postDelayed({ // Runnble 객체와 time을 파라미터로 받는다
                    Glide.with(binding.homePlantImage)
                        .asGif()
                        .load(R.raw.wither_min_android)
                        .into(binding.homePlantImage)
                }, 2000)
            }
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
        intent.putExtra("cherishId", viewModel.selectedCherishUser.value?.id)
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

