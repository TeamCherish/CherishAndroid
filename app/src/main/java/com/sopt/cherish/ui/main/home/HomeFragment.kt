package com.sopt.cherish.ui.main.home

import android.animation.ArgbEvaluator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentHomeBinding
import com.sopt.cherish.databinding.MainCherryItemBinding
import com.sopt.cherish.remote.api.User
import com.sopt.cherish.remote.api.UserResult
import com.sopt.cherish.ui.adapter.HomeCherryListAdapter
import com.sopt.cherish.ui.adapter.OnItemClickListener
import com.sopt.cherish.ui.detail.DetailPlantActivity
import com.sopt.cherish.ui.dialog.WateringDialogFragment
import com.sopt.cherish.ui.enrollment.EnrollmentPhoneActivity
import com.sopt.cherish.ui.main.MainViewModel
import com.sopt.cherish.util.GridItemDecorator
import com.sopt.cherish.util.PixelUtil.dp
import com.sopt.cherish.util.extension.longToast


/**
 * 메인 홈뷰
 * 초기상태와 중간에 있는 경우 2개 다 고려해야 합니다.
 * todo : 1. 아무것도 등록안됐을때 상태 , 2. 바텀시트 클릭 시 클릭된게 맨 앞에서 보여지게 하는거
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
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mainViewModel = viewModel
        homeCherryListAdapter = HomeCherryListAdapter(this)

        standardBottomSheetBehavior =
            BottomSheetBehavior.from(binding.homeStandardBottomSheet)
        addBottomSheetCallback()
        initializeRecyclerView(homeCherryListAdapter)

        binding.homeWateringBtn.setOnClickListener {
            navigateWatering(viewModel.selectedCherishUser.value?.id!!)
        }

        binding.homeUserAddText.setOnClickListener {
            navigatePhoneBook()
        }

        binding.homeMovePlantDetail.setOnClickListener {
            navigateDetailPlant(
                viewModel.cherishuserId.value!!,
                viewModel.selectedCherishUser.value?.id!!
            )
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeCherishUsers()
        observeAnimationTrigger()
    }

    private fun observeAnimationTrigger() {
        // 이걸 언제 값을 바꿔줘야 할지를 생각해야한다.
        // 만약 review를 주었을때 이값을 true로 바꿔줘야한다던지 라던가
        // 그처럼 말이다.
        // 근데 문제는 review를 액티비티로 변경하면서 생기는 문제점인데
        // 값을 공유할 수 없기 때문에 이를 분리하기가 어렵다.
        // todo : 물주는 애니메이션 이나 시드는 애니메이션에 따라 작업해야합니다.
        viewModel.animationTrigger.observe(viewLifecycleOwner) {
            if (it) {
                longToast(requireContext(), "식물 물주는 애니메이션 등장!")
            } else {
                longToast(requireContext(), "식물 시드는 애니메이션 등장!")
            }
        }
    }

    private fun observeCherishUsers() {
        viewModel.cherishUsers.observe(viewLifecycleOwner) {
            setCherishUserListAdapter(it)
            setSelectedUser(it.userData.userList.reversed()[0])
        }
    }

    private fun setSelectedUser(user: User) {
        viewModel.selectedCherishUser.value = user
        homeCherryListAdapter.lastSelectedPosition = 0
    }

    private fun setCherishUserListAdapter(userResult: UserResult) {
        homeCherryListAdapter.data = userResult.userData.userList.reversed() as MutableList<User>
        homeCherryListAdapter.notifyDataSetChanged()
    }

    override fun onItemClick(itemBinding: MainCherryItemBinding, position: Int) {
        viewModel.selectedCherishUser.value = homeCherryListAdapter.data[position]
        slideDownBottomSheet()
    }

    private fun initializeRecyclerView(
        homeCherryListAdapter: HomeCherryListAdapter
    ) {
        binding.homeUserList.apply {
            adapter = homeCherryListAdapter
            layoutManager = GridLayoutManager(context, 5)
            addItemDecoration(GridItemDecorator(spanCount = 5, spacing = 10.dp, includeEdge = true))
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
        }
    }

    // 화면이동
    private fun navigateWatering(id: Int) {
        if (viewModel.selectedCherishUser.value?.dDay!! <= 0) {
            WateringDialogFragment(id).show(parentFragmentManager, TAG)
        } else {
            longToast(requireContext(), "물 줄수있는 날이 아니에요 ㅠ")
        }
    }

    private fun navigatePhoneBook() {
        // phoneBook
        val intent = Intent(context, EnrollmentPhoneActivity::class.java)
        intent.putExtra("userId", viewModel.cherishuserId.value)
        startActivity(intent)
    }

    private fun navigateDetailPlant(userId: Int?, cherishId: Int?) {
        val intent = Intent(activity, DetailPlantActivity::class.java)
        // todo : Parcelable로 변경해서 보내주도록 하자
        intent.putExtra("userId", userId)
        intent.putExtra("cherishId", viewModel.selectedCherishUser.value?.id)
        intent.putExtra("cherishUserPhoneNumber", viewModel.selectedCherishUser.value?.phoneNumber)
        intent.putExtra("cherishNickname", viewModel.selectedCherishUser.value?.nickName)
        intent.putExtra("userNickname", viewModel.userNickName.value)
        intent.putExtra("cherishuserId", viewModel.cherishuserId.value)
        startActivity(intent)
    }

    // 리사이클러뷰 아이템 클릭 시 바텀 시트 내려감
    private fun slideDownBottomSheet() {
        // todo : 비율로 변경해야함
        standardBottomSheetBehavior.apply {
            state = BottomSheetBehavior.STATE_COLLAPSED
            peekHeight = 150.dp
            expandedOffset = 100.dp
        }
    }

    // 바텀시트 뒤에 녀석 색상 변경
    private fun transitionBottomSheetParentView(slideOffset: Float) {
        val argbEvaluator =
            ArgbEvaluator().evaluate(slideOffset, R.color.transparent, R.color.black)
    }

    private fun addBottomSheetCallback() {
        standardBottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (standardBottomSheetBehavior.state == BottomSheetBehavior.STATE_DRAGGING)
                    standardBottomSheetBehavior.peekHeight = 60.dp
            }
        })
    }

    companion object {
        private val TAG = "HomeFragment"
    }
}

