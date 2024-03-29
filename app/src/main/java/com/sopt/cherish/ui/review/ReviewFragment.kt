package com.sopt.cherish.ui.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentReviewBinding
import com.sopt.cherish.remote.api.ReviewWateringReq
import com.sopt.cherish.ui.main.MainViewModel
import com.sopt.cherish.util.MultiViewDialog
import com.sopt.cherish.util.extension.FlexBoxExtension.getChip
import com.sopt.cherish.util.extension.FlexBoxExtension.getChipsCount
import com.sopt.cherish.util.extension.countNumberOfCharacters
import com.sopt.cherish.util.extension.writeKeyword
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ReviewFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private val reviewNotificationViewModel by lazy {
        ViewModelProvider(this).get(ReviewNotificationViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentReviewBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_review, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mainViewModel = viewModel
        reviewNotificationViewModel.startNotificationTimer()
        activity?.findViewById<BottomNavigationView>(R.id.main_bottom_navi)?.visibility =
            View.INVISIBLE

        addUserStatusWithChip(binding)
        addLimitNumberOfKeywordCharacters(binding)
        addLimitNumberOfMemoCharacters(binding)
        sendReviewToServer(binding)
        ignoreSendReviewToServer(binding)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.findViewById<BottomNavigationView>(R.id.main_bottom_navi)?.visibility =
            View.VISIBLE
        reviewNotificationViewModel.cancel()
    }

    private fun addLimitNumberOfMemoCharacters(binding: FragmentReviewBinding) {
        binding.homeReviewMemo.countNumberOfCharacters { memo ->
            binding.homeReviewNumberOfMemo.text = memo?.length.toString()
        }
    }

    private fun addLimitNumberOfKeywordCharacters(binding: FragmentReviewBinding) {
        binding.homeReviewEditKeyword.countNumberOfCharacters { keyword ->
            binding.homeReviewNumberOfCharacters.text = keyword?.length.toString()
        }
    }

    private fun showLoadingDialog() {
        lifecycleScope.launch {
            val dialog = MultiViewDialog(R.layout.dialog_loading, 0.35f, 0.169f)
            dialog.show(parentFragmentManager, TAG)
            delay(2000)
            dialog.dismiss()
            viewModel.isWatered.value = true
            parentFragmentManager.beginTransaction().remove(this@ReviewFragment).commit()
            viewModel.delayFetchUsers()
            parentFragmentManager.popBackStack()
        }
    }

    private fun addUserStatusWithChip(binding: FragmentReviewBinding) {
        binding.homeReviewEditKeyword.writeKeyword(binding.homeReviewFlexBox, parentFragmentManager)
    }

    private fun sendReviewToServer(binding: FragmentReviewBinding) {
        binding.homeReviewAdminAccept.setOnClickListener {
            if (binding.homeReviewFlexBox.getChipsCount() == 0 && binding.homeReviewMemo.text.isEmpty()) {
                MultiViewDialog(
                    R.layout.dialog_warning_review_no_word_warning,
                    0.6944f,
                    0.16875f
                ).show(
                    parentFragmentManager,
                    TAG
                )
            } else {
                if (binding.homeReviewMemo.text.length <= 100) {
                    reviewNotificationViewModel.cancel()

                    // todo : 이거 지금 실패하고 있는거 같아요.
                    viewModel.remindReviewNotificationToServer(viewModel.selectedCherishUser.value!!.id)
                    viewModel.sendReviewToServer(
                        ReviewWateringReq(
                            binding.homeReviewMemo.text.toString(),
                            if (binding.homeReviewFlexBox.getChip(0) == null) "" else binding.homeReviewFlexBox.getChip(
                                0
                            )!!.text.toString(),
                            if (binding.homeReviewFlexBox.getChip(1) == null) "" else binding.homeReviewFlexBox.getChip(
                                1
                            )!!.text.toString(),
                            if (binding.homeReviewFlexBox.getChip(2) == null) "" else binding.homeReviewFlexBox.getChip(
                                2
                            )!!.text.toString(),
                            viewModel.selectedCherishUser.value?.id!!
                        )
                    )
                    showLoadingDialog()
                } else {
                    MultiViewDialog(
                        R.layout.dialog_warning_review_limit_error,
                        0.6944f,
                        0.16875f
                    ).show(
                        parentFragmentManager,
                        TAG
                    )
                }
            }
        }
    }

    private fun ignoreSendReviewToServer(binding: FragmentReviewBinding) {
        binding.homeReviewIgnoreAccept.setOnClickListener {
            reviewNotificationViewModel.cancel()
            viewModel.remindReviewNotificationToServer(viewModel.selectedCherishUser.value!!.id)
            viewModel.sendReviewToServer(
                reviewWateringReq = ReviewWateringReq(
                    null,
                    null,
                    null,
                    null,
                    viewModel.selectedCherishUser.value?.id!!
                )
            )
            showLoadingDialog()
        }
    }

    companion object {
        const val TAG = "ReviewFragment"
    }
}