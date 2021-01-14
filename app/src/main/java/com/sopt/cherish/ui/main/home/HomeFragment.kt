package com.sopt.cherish.ui.main.home

import android.animation.ArgbEvaluator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
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
import com.sopt.cherish.util.extension.shortToast


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
    // 식물 등록 userId
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.mainViewModel = viewModel
        initializeView()
        val homeCherryListAdapter = HomeCherryListAdapter(this)
        viewModel.fetchUsers()
        initializeBottomSheetBehavior()

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
            navigateDetailPlant()
        }

        setAdapterData(homeCherryListAdapter)
        initializeRecyclerView(homeCherryListAdapter)
        updateProgressBar()
        return binding.root
    }

    private fun initializeView() {
        // 나의 소중한 사람들 count

        // HomeRemainDate는 user의 각 dDay를 보여주면 될거 같음
        viewModel.cherishUsers.observe(viewLifecycleOwner) {
            binding.homeCherryNumber.text = it.userData.totalUser.toString()
        }
        // 유저 닉네임은 가지고 있다가 review화면에서 보여줘야 함
    }

    private fun navigateDetailPlant() {
        val intent = Intent(requireContext(), DetailPlantActivity::class.java)
        startActivity(intent)
    }

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
        viewModel.fetchUsers()
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
        startActivity(intent)
    }

    private fun transitionBottomSheetParentView(slideOffset: Float) {
        val argbEvaluator =
            ArgbEvaluator().evaluate(slideOffset, R.color.white, R.color.black)
        binding.homeFragment.setBackgroundColor(argbEvaluator as Int)
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

    // recyclerView 아이템이 클릭되었을 때 변경되어야 할 것은?
    // 1. dDay home_remain_date -> O
    // 2. 아직 수명이 탄탄한 과 같은 유저의 상태 메시지 home_selected_user_status -> O
    // 3. 남쿵둥이와 같은 유저의 닉네임 home_selected_user_name -> O
    // 4. 유저 식물 -> 이건 서버에서 url을 줄거니까 bindingAdapter를 이용해서 사용할 수 있습니다. -> O
    // 5. 배경 색 -> 이건 어쩔 수 없이 뷰 코드에서 진행 -> O
    // 빠트린건 없나 다시 생각
    @SuppressLint("SetTextI18n")
    override fun onItemClick(itemBinding: MainCherryItemBinding, user: User) {
        // bottomSheetBehavior 초기화 , 안되는데 왜 안되는 지는 모르겠음
        standardBottomSheetBehavior.peekHeight = 60.dp
        // 클릭 이벤트 다시한번 생각
        if (itemBinding.userImgSelected.visibility == View.INVISIBLE) {
            itemBinding.userImgSelected.visibility = View.VISIBLE
        } else {
            itemBinding.userImgSelected.visibility = View.INVISIBLE
        }
        // 뷰모델에 선택된 유저의 상황 넣어주기
        viewModel.cherishUser.value = user
        // cherishId를 쏴줘야지
        // 선택된 유저 이름 변경
        // 음수로 가면 -> -1 이면 , 물주기 까지 하루 남음
        // 양수로 가면 -> 이미 그만큼 지난거임 , if 7 이면 물줘야하는게 7일지난거임
        // 유저 닉네임
        viewModel.cherishUser.observe(viewLifecycleOwner) {
            // 선택된 유저 상태
            binding.homeSelectedUserStatus.text = user.plantModifier
            // 선택된 유저 닉네임
            binding.homeSelectedUserName.text = user.nickName
            // 선택된 유저 물주기 날짜
            when {
                user.dDay < 0 -> {
                    binding.homeRemainDate.text = "D${viewModel.cherishUser.value!!.dDay}"
                }
                user.dDay > 0 -> {
                    binding.homeRemainDate.text = "D+${viewModel.cherishUser.value!!.dDay}"
                }
                else -> {
                    binding.homeRemainDate.text = "D-Day"
                }
            }
            binding.homeAffectionRating.text = viewModel.cherishUser.value!!.growth.toString()
            binding.homeAffectionProgressbar.progress = viewModel.cherishUser.value!!.growth
        }

        // 이미지 애니메이션 테스트 , gif , server에서 받아서 해도 됨 지금은 그냥 raw파일에서 받아서 넣어준 상태
        if (user.plantAnimationUrl != "없지롱") {
            shortToast(requireContext(), "없지롱!")
        } else {
            Glide.with(binding.homePlantImage)
                .asGif()
                .load(R.raw.real_min_ver2)
                .into(binding.homePlantImage)
        }
    }
}