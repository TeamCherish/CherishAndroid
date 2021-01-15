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

    // todo : 최대한 디자이너들이 작업한거 보여줄 수 있게 퍼포먼스 보여주는 방법 생각
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.mainViewModel = viewModel

        val homeCherryListAdapter = HomeCherryListAdapter(this)
        viewModel.fetchUsers()

        initializeView()
        initializeBottomSheetBehavior()
        setAdapterData(homeCherryListAdapter)
        initializeRecyclerView(homeCherryListAdapter)
        updateProgressBar()

        // onClick
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
        return binding.root
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
        binding.homeFragment.setBackgroundColor(
            // 하드코딩
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

    // 최초 화면이 보여질때
    private fun initializeView() {
        viewModel.cherishUsers.observe(viewLifecycleOwner) {
            viewModel.cherishUser.value = it.userData.userList[0]
            binding.homeCherryNumber.text = it.userData.totalUser.toString()
            it.userData.userList[0].apply {
                initializeViewOnItemClick(this)
            }
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

    // navigate
    private fun navigateWatering() {
        WateringDialogFragment().show(parentFragmentManager, TAG)
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
        intent.putExtra("cherishId", cherishId)
        startActivity(intent)
    }

    // progressBar
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
    override fun onItemClick(itemBinding: MainCherryItemBinding, position: Int) {
        viewModel.cherishUsers.observe(viewLifecycleOwner) {
            // contact Dialog
            viewModel.cherishUser.value = it.userData.userList[position]
            it.userData.userList[position].apply {
                initializeViewOnItemClick(this)
            }.also { positionUser ->
                viewModel.cherishUser.value = positionUser
            }
        }
    }

    // 유저 클릭 시 보여지는 화면
    @SuppressLint("SetTextI18n")
    private fun initializeViewOnItemClick(user: User) {
        binding.homeSelectedUserName.text = user.nickName
        binding.homeSelectedUserStatus.text = user.plantModifier
        binding.homeAffectionRating.text = "${user.growth}%"
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
        // todo : gif처리하는것만 좀 하면 될거 같음
        // todo : 정확하게 식물의 3단계를 표현할 것인지? 혹은 gif를 보여줄 것인지?

        // todo : 일단 gif부터 시작
        Glide.with(requireContext())
            .load(viewModel.normalFlowerAnimationUri)
            .into(binding.homePlantImage)
    }

    // 바텀시트 뒤에 녀석 색상 변경
    private fun transitionBottomSheetParentView(slideOffset: Float) {
        val argbEvaluator =
            ArgbEvaluator().evaluate(slideOffset, R.color.white, R.color.black)
        binding.homeFragment.setBackgroundColor(argbEvaluator as Int)
    }
}