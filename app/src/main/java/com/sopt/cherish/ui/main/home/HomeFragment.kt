package com.sopt.cherish.ui.main.home

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
 * Created on 2021-03-05 by SSong-develop
 * 메인 홈뷰
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
        homeCherryListAdapter = HomeCherryListAdapter(this, viewModel)
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
                viewModel.cherishUserId.value!!
            )
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeCherishUsers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchUsers()
    }

    private fun observeCherishUsers() {
        viewModel.cherishUsers.observe(viewLifecycleOwner) {
            if (it != null) {
                setCherishUserListAdapter(it)
                try {
                    setSelectedUser(it.userData.userList[viewModel.cherishSelectedPosition.value!!])
                } catch (exception: Exception) {
                    setSelectedUser(it.userData.userList[1])
                }

            } else {
                val blankIntent = Intent(requireContext(), HomeBlankActivity::class.java)
                blankIntent.putExtra("userNickname", viewModel.userNickName.value)
                blankIntent.putExtra("userId", viewModel.cherishUserId.value)
                blankIntent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                startActivity(blankIntent)
                requireActivity().finish()
            }
        }
    }

    private fun setSelectedUser(user: User) {
        viewModel.selectedCherishUser.value = user
        try {
            homeCherryListAdapter.data[0] =
                homeCherryListAdapter.data[viewModel.cherishSelectedPosition.value!!]
        } catch (exception: Exception) {
            homeCherryListAdapter.data[0] =
                homeCherryListAdapter.data[1]
        }

    }

    private fun setCherishUserListAdapter(userResult: UserResult) {
        homeCherryListAdapter.data = (userResult.userData.userList as? MutableList<User>)
            ?: throw IllegalArgumentException("list is Empty")
        homeCherryListAdapter.notifyDataSetChanged()
    }

    override fun onItemClick(itemBinding: MainCherryItemBinding, position: Int) {
        viewModel.selectedCherishUser.value = homeCherryListAdapter.data[position]
        homeCherryListAdapter.data[0] = homeCherryListAdapter.data[position]
        viewModel.cherishSelectedPosition.value = position
        homeCherryListAdapter.notifyItemChanged(0)
        slideDownBottomSheet()
    }

    private fun initializeRecyclerView(
        homeCherryListAdapter: HomeCherryListAdapter
    ) {
        binding.homeUserList.apply {
            adapter = homeCherryListAdapter
            layoutManager = GridLayoutManager(context, 5)
            addItemDecoration(GridItemDecorator(spanCount = 5, spacing = 6.dp, includeEdge = true))
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
        }
    }

    // 화면이동
    private fun navigateWatering() {
        if (viewModel.selectedCherishUser.value?.dDay!! <= 0) {
            WateringDialogFragment().show(parentFragmentManager, TAG)
        } else {
            longToast(requireContext(), "물 줄수있는 날이 아니에요 ㅠ")
        }
    }

    private fun navigatePhoneBook() {
        val intent = Intent(context, EnrollmentPhoneActivity::class.java)
        intent.putExtra("userId", viewModel.cherishUserId.value)
        startActivity(intent)
    }

    private fun navigateDetailPlant(userId: Int?) {
        val intent = Intent(activity, DetailPlantActivity::class.java)
        intent.putExtra("userId", userId)
        intent.putExtra("cherishId", viewModel.selectedCherishUser.value?.id)
        intent.putExtra("cherishUserPhoneNumber", viewModel.selectedCherishUser.value?.phoneNumber)
        intent.putExtra("cherishNickname", viewModel.selectedCherishUser.value?.nickName)
        intent.putExtra("userNickname", viewModel.userNickName.value)
        intent.putExtra("cherishuserId", viewModel.cherishUserId.value)
        intent.putExtra("selectedUserDday", viewModel.selectedCherishUser.value!!.dDay)
        startActivityForResult(intent, CODE_MOVE_DETAIL_PLANT)
    }

    private fun slideDownBottomSheet() {
        standardBottomSheetBehavior.apply {
            state = BottomSheetBehavior.STATE_COLLAPSED
            peekHeight = 160.dp
            expandedOffset = 100.dp
        }
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
                viewModel.isWatered.value = data?.getBooleanExtra("animationTrigger", false)
            }
        }
    }

    companion object {
        private const val TAG = "HomeFragment"
        private const val CODE_MOVE_DETAIL_PLANT = 1005
    }
}