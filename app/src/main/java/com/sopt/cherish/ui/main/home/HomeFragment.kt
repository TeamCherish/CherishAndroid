package com.sopt.cherish.ui.main.home

import android.animation.ArgbEvaluator
import android.app.Activity.RESULT_OK
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
import com.sopt.cherish.ui.dialog.wateringdialog.WateringDialogFragment
import com.sopt.cherish.ui.enrollment.EnrollmentPhoneActivity
import com.sopt.cherish.ui.main.MainViewModel
import com.sopt.cherish.util.GridItemDecorator
import com.sopt.cherish.util.PixelUtil.dp
import com.sopt.cherish.util.extension.longToast


/**
 * 메인 홈뷰
 * 초기상태와 중간에 있는 경우 2개 다 고려해야 합니다.
 * todo : 1. 바텀시트 클릭 시 클릭된게 맨 앞에서 보여지게 하는거
 * todo : fetchUser() 할때마다 selectedUser가 갱신되는게 좀 마음이 아프긴 해요;;; 이거 어떻게 해결할 방법만 좀 찾으면...
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
            navigateWatering()
        }

        binding.homeUserAddText.setOnClickListener {
            navigatePhoneBook()
        }

        binding.homeMovePlantDetail.setOnClickListener {
            navigateDetailPlant(
                viewModel.cherishuserId.value!!
            )
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeCherishUsers()
        observeAnimationTrigger()
    }

    private fun observeAnimationTrigger() {
        // 값 보내주는건 끝났구 이제 애니메이션 보여주기만 하면 끝!
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
        // null이 오는데 null일 리가 없는데 왜 null이라고 나오는지를 모르겠어...
        // 왜 null이라고 하는거지....분명 연결을 했는데...
        homeCherryListAdapter.data = (userResult.userData.userList.reversed() as? MutableList<User>)
            ?: throw IllegalArgumentException("list is Empty")
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
    private fun navigateWatering() {
        // +로 가는 녀석들이 가장 물주기가 시급한 친구들이라고해서 일단 알고리즘을 이렇게 작성함.
        if (viewModel.selectedCherishUser.value?.dDay!! >= 0) {
            WateringDialogFragment().show(parentFragmentManager, TAG)
        } else {
            longToast(requireContext(), "물 줄수있는 날이 아니에요 ㅠ")
        }
    }

    private fun navigatePhoneBook() {
        val intent = Intent(context, EnrollmentPhoneActivity::class.java)
        intent.putExtra("userId", viewModel.cherishuserId.value)
        startActivity(intent)
    }

    private fun navigateDetailPlant(userId: Int?) {
        val intent = Intent(activity, DetailPlantActivity::class.java)
        intent.putExtra("userId", userId)
        intent.putExtra("cherishId", viewModel.selectedCherishUser.value?.id)
        intent.putExtra("cherishUserPhoneNumber", viewModel.selectedCherishUser.value?.phoneNumber)
        intent.putExtra("cherishNickname", viewModel.selectedCherishUser.value?.nickName)
        intent.putExtra("userNickname", viewModel.userNickName.value)
        intent.putExtra("cherishuserId", viewModel.cherishuserId.value)
        // todo : 이걸 startActivityForResult로 고친다음에 detailPlantActivity가 finish 될때의 값을 가져와 이를 animationTrigger에 담아준다
        startActivityForResult(intent, CODE_MOVE_DETAIL_PLANT)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CODE_MOVE_DETAIL_PLANT) {
            if (resultCode == RESULT_OK) {
                viewModel.animationTrigger.value = data?.getBooleanExtra("animationTrigger", false)
            }
        }
    }

    companion object {
        private const val TAG = "HomeFragment"
        private const val CODE_MOVE_DETAIL_PLANT = 1005
    }
}

