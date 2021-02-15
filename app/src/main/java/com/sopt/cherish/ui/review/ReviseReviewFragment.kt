package com.sopt.cherish.ui.review

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentReviseReviewBinding
import com.sopt.cherish.remote.api.DeleteReviewReq
import com.sopt.cherish.remote.api.ReviseReviewReq
import com.sopt.cherish.ui.detail.DetailPlantActivity
import com.sopt.cherish.ui.detail.DetailPlantViewModel
import com.sopt.cherish.ui.dialog.CustomDialogFragment
import com.sopt.cherish.util.DateUtil
import com.sopt.cherish.util.extension.FlexBoxExtension.addChip
import com.sopt.cherish.util.extension.FlexBoxExtension.getChip
import com.sopt.cherish.util.extension.countNumberOfCharacters
import com.sopt.cherish.util.extension.longToast
import com.sopt.cherish.util.extension.shortToast
import com.sopt.cherish.util.extension.writeReview

/**
 * Created by SSong-develop on 2021-02-12
 * todo : binding을 전역으로 만든 다음에 resume으로 돌아왔을 떄
 */
class ReviseReviewFragment : Fragment() {
    private val viewModel: DetailPlantViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentReviseReviewBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_revise_review, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        setHasOptionsMenu(true)
        observeSelectedDate(binding)
        addLimitNumberOfMemoCharacters(binding)
        addLimitNumberOfKeywordCharacters(binding)
        addUserStatusWithChip(binding)

        binding.reviseReviewAdminAccept.setOnClickListener {
            reviseReview(binding)
        }
        return binding.root
    }

    private fun reviseReview(binding: FragmentReviseReviewBinding) {
        viewModel.sendReviseReviewToServer(
            ReviseReviewReq(
                viewModel.cherishId.value!!,
                binding.reviseReviewDateText.text.toString(),
                binding.reviseReviewMemo.text.toString(),
                binding.reviseReviewFlexBox.getChip(0)?.text.toString(),
                binding.reviseReviewFlexBox.getChip(1)?.text.toString(),
                binding.reviseReviewFlexBox.getChip(2)?.text.toString()
            )
        )
        longToast(requireContext(), "메모 수정이 되었어요!!")
        parentFragmentManager.popBackStack()
    }

    private fun observeSelectedDate(binding: FragmentReviseReviewBinding) {
        viewModel.selectedCalendarData.observe(viewLifecycleOwner) {
            binding.reviseReviewDateText.text = DateUtil.convertDateToString(it.wateredDate)
            binding.reviseReviewFlexBox.apply {
                addChip(it.userStatus1)
                addChip(it.userStatus2)
                addChip(it.userStatus3)
            }
            binding.reviseReviewMemo.setText(it.review)
        }
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as DetailPlantActivity).setActionBarTitle("메모 수정")
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.getItem(0).isVisible = false
        menu.getItem(1).isVisible = false // invisible menuitem 2
        menu.getItem(2).isVisible = true // invisible menuitem 2

        (activity as DetailPlantActivity).invalidateOptionsMenu()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                return true
            }
            R.id.trash -> {
                // 메모 삭제하기를 실행한다.
                viewModel.deleteReview(
                    DeleteReviewReq(
                        viewModel.cherishId.value!!,
                        DateUtil.convertDateToString(viewModel.selectedCalendarData.value!!.wateredDate)
                    )
                )
                longToast(requireContext(), "메모 삭제에 성공했습니다.")
                parentFragmentManager.popBackStack()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addLimitNumberOfMemoCharacters(binding: FragmentReviseReviewBinding) {
        binding.reviseReviewMemo.countNumberOfCharacters { memo ->
            binding.reviseReviewNumberOfMemo.text = memo?.length.toString()
            if (memo?.length!! > 100) {
                // todo : dialog로 보여줘야 함
                shortToast(requireContext(), "100자를 초과했습니다.")
            }
        }
    }

    private fun addLimitNumberOfKeywordCharacters(binding: FragmentReviseReviewBinding) {
        binding.reviseReviewEditKeyword.countNumberOfCharacters { keyword ->
            binding.reviseReviewNumberOfCharacters.text = keyword?.length.toString()
            if (keyword?.length!! > 5) {
                CustomDialogFragment(R.layout.sample_wordcount_error).show(
                    parentFragmentManager,
                    ReviewActivity.TAG
                )
            }
        }
    }

    private fun addUserStatusWithChip(binding: FragmentReviseReviewBinding) {
        binding.reviseReviewEditKeyword.writeReview(binding.reviseReviewFlexBox)
    }

}