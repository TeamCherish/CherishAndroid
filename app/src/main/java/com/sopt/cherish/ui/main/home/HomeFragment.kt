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
import com.sopt.cherish.ui.datail.DetailPlantActivity
import com.sopt.cherish.ui.dialog.WateringDialogFragment
import com.sopt.cherish.ui.enrollment.EnrollmentPhoneActivity
import com.sopt.cherish.ui.main.MainViewModel
import com.sopt.cherish.util.PixelUtil
import com.sopt.cherish.util.PixelUtil.dp
import com.sopt.cherish.util.PixelUtil.pixel


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

    private val debug =
        "https://sopt27.s3.ap-northeast-2.amazonaws.com/%E1%84%8B%E1%85%A7%E1%86%BC%E1%84%8B%E1%85%B3%E1%86%AB%E1%84%8B%E1%85%A1%E1%84%8B%E1%85%B5%E1%84%80%E1%85%A5%E1%84%82%E1%85%B3%E1%86%AB+main_bg/main_img_min.png"

    companion object {
        private val TAG = "HomeFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.mainViewModel = viewModel
        viewModel.fetchUsers()
        homeCherryListAdapter = HomeCherryListAdapter(this)

        // 뷰가 처음 보일 떄
        initializeView()
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
            navigateWatering(viewModel.cherishUser.value?.id!!)
        }

        binding.homeUserAddText.setOnClickListener {
            navigatePhoneBook()
        }

        binding.homeMovePlantDetail.setOnClickListener {
            navigateDetailPlant(viewModel.userId.value!!, viewModel.cherishUser.value?.id!!)
        }

        return binding.root
    }

    private fun observeWateringOrDelayAnimation() {
        viewModel.animationTrigger.observe(viewLifecycleOwner) {
            // 그 안에 클릭을 해야합니다.
            if (it) {
                Handler(Looper.getMainLooper()).postDelayed({ // Runnble 객체와 time을 파라미터로 받는다
                    Glide.with(binding.homePlantImage)
                        .asGif()
                        .load(R.raw.watering_min_android)
                        .into(binding.homePlantImage)
                }, 2000)
                initializeView()
            } else {
                Handler(Looper.getMainLooper()).postDelayed({ // Runnble 객체와 time을 파라미터로 받는다
                    Glide.with(binding.homePlantImage)
                        .asGif()
                        .load(R.raw.wither_min_android)
                        .into(binding.homePlantImage)
                }, 2000)
                initializeView()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchUsers()
        homeCherryListAdapter.notifyDataSetChanged()
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
        intent.putExtra("userId", userId)
        intent.putExtra("cherishId", viewModel.cherishUser.value?.id)
        intent.putExtra("cherishUserPhoneNumber", viewModel.cherishUser.value?.phoneNumber)
        intent.putExtra("cherishNickname", viewModel.cherishUser.value?.nickName)
        intent.putExtra("userNickname", viewModel.userNickName.value)
        intent.putExtra("userId", viewModel.userId.value)
        startActivity(intent)
    }

    private fun initializeBottomSheetBehavior() {
        standardBottomSheetBehavior =
            BottomSheetBehavior.from(binding.homeStandardBottomSheet)
        // bottom sheet state 지정
        standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        standardBottomSheetBehavior.peekHeight = 60.dp
        standardBottomSheetBehavior.expandedOffset = 100.dp
        standardBottomSheetBehavior.halfExpandedRatio = 0.23f
        standardBottomSheetBehavior.isHideable = false

        standardBottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                /*transitionBottomSheetParentView(slideOffset)*/
            }
        })
    }

    private fun setAdapterData(homeCherryListAdapter: HomeCherryListAdapter) {
        viewModel.cherishUsers.observe(viewLifecycleOwner) {
            homeCherryListAdapter.data = it.userData.userList as MutableList<User>
            homeCherryListAdapter.notifyDataSetChanged()
        }
    }

    // 최초 화면이 보여질때
    private fun initializeView() {
        /*viewModel.cherishUsers.observe(viewLifecycleOwner) {
            viewModel.cherishUser.value = it.userData.userList[0]
            binding.homeCherryNumber.text = it.userData.totalUser.toString()
            it.userData.userList[0].apply {
                initializeViewOnItemClick(this)
            }
        }*/
        // 가라입니다 가라
        Glide.with(binding.homePlantImage)
            .load(R.drawable.stuki_temp)
            .into(binding.homePlantImage)

        viewModel.cherishUsers.observe(viewLifecycleOwner) {
            binding.homeCherryNumber.text = it.userData.totalUser.toString()
        }

        binding.homeRemainDate.text = "D+1"
        binding.homeSelectedUserStatus.text = "반짝반짝 빛이나는"
        binding.homeSelectedUserName.text = "영탁언니"
        binding.homeAffectionProgressbar.progress = 58
        binding.homeAffectionRating.text = "58%"
    }


    private fun initializeRecyclerView(
        homeCherryListAdapter: HomeCherryListAdapter
    ) {
        binding.homeUserList.apply {
            adapter = homeCherryListAdapter
            layoutManager = GridLayoutManager(context, 5)
        }
    }

    // recyclerview Item click event
    override fun onItemClick(itemBinding: MainCherryItemBinding, position: Int) {
        standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        standardBottomSheetBehavior.peekHeight = 60.dp
        standardBottomSheetBehavior.expandedOffset = 100.dp
        viewModel.cherishUsers.observe(viewLifecycleOwner) {
            viewModel.cherishUser.value = it.userData.userList[position]
            it.userData.userList[position].apply {
                initializeViewOnItemClick(this)
            }
        }
    }

    // 유저 클릭 시 보여지는 화면
    @SuppressLint("SetTextI18n")
    private fun initializeViewOnItemClick(user: User) {
        binding.homeSelectedUserName.text = user.nickName
        binding.homeSelectedUserStatus.text = user.plantModifier
        binding.homeAffectionRating.text = "${user.growth}%"
        updateProgressBar(user.growth)
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
        if (viewModel.cherishUser.value!!.homeMainBackgroundImageUrl == debug) {
            Glide.with(requireContext())
                .load(viewModel.cherishUser.value!!.plantAnimationUrl)
                .override(PixelUtil.screenWidth.pixel, PixelUtil.screenHeight.pixel)
                .into(binding.homePlantImage)
        } else {
            Glide.with(requireContext())
                .load(viewModel.cherishUser.value!!.homeMainBackgroundImageUrl)
                .override(PixelUtil.screenWidth.pixel, PixelUtil.screenHeight.pixel)
                .into(binding.homePlantImage)
        }
    }

    // progressBar
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
        binding.homeAffectionProgressbar.progress = rating
    }

    // 바텀시트 뒤에 녀석 색상 변경
    private fun transitionBottomSheetParentView(slideOffset: Float) {
        val argbEvaluator =
            ArgbEvaluator().evaluate(slideOffset, R.color.cherish_gray, R.color.black)
        binding.homeFragment.setBackgroundColor(argbEvaluator as Int)
    }
}