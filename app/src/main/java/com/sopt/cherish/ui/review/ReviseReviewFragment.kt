package com.sopt.cherish.ui.review

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
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
import com.sopt.cherish.util.SimpleLogger
import com.sopt.cherish.util.extension.*
import com.sopt.cherish.util.extension.FlexBoxExtension.addChip
import com.sopt.cherish.util.extension.FlexBoxExtension.getChip

/**
 * Created by SSong-develop on 2021-02-12
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
            binding.reviseReviewDateText.text = it?.wateredDate?.let { it1 ->
                DateUtil.convertDateToString(
                    it1
                )
            }
            binding.reviseReviewFlexBox.apply {
                it?.userStatus1?.let { it1 -> addChip(it1) }
                it?.userStatus2?.let { it1 -> addChip(it1) }
                it?.userStatus3?.let { it1 -> addChip(it1) }
            }
            binding.reviseReviewMemo.setText(it?.review)
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
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("삭제 하시겠습니까?").setMessage("삭제 시 리뷰를 찾을 수 없습니다")
                    .setPositiveButton(
                        "확인"
                    ) { dialog, which ->
                        viewModel.deleteReview(
                            DeleteReviewReq(
                                viewModel.cherishId.value!!,
                                DateUtil.convertDateToString(viewModel.selectedCalendarData.value!!.wateredDate)
                            )
                        )
                        viewModel.selectedCalendarData.value = null
                        SimpleLogger.logI("${viewModel.selectedCalendarData.value}")
                        longToast(requireContext(), "메모 삭제에 성공했습니다.")
                        parentFragmentManager.popBackStack()
                    }
                    .setNegativeButton("취소") { dialog, which ->
                        shortToast(requireContext(), "메모 삭제를 취소했습니다.")
                        dialog.dismiss()
                    }
                builder.create().show()
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
                binding.reviseReviewMemo.hideKeyboard()
            }
        }
    }

    private fun addLimitNumberOfKeywordCharacters(binding: FragmentReviseReviewBinding) {
        binding.reviseReviewEditKeyword.countNumberOfCharacters { keyword ->
            binding.reviseReviewNumberOfCharacters.text = keyword?.length.toString()
            if (keyword?.length!! > 5) {
                CustomDialogFragment(R.layout.dialog_keyword_limit_error).show(
                    parentFragmentManager,
                    ReviewActivity.TAG
                )
            }
        }
    }

    private fun addUserStatusWithChip(binding: FragmentReviseReviewBinding) {
        binding.reviseReviewEditKeyword.writeKeyword(
            binding.reviseReviewFlexBox,
            parentFragmentManager
        )
    }

}