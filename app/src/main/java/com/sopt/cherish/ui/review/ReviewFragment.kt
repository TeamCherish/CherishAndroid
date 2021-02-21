package com.sopt.cherish.ui.review

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivityReviewBinding
import com.sopt.cherish.remote.api.ReviewWateringReq
import com.sopt.cherish.ui.dialog.CustomDialogFragment
import com.sopt.cherish.ui.main.MainViewModel
import com.sopt.cherish.util.extension.FlexBoxExtension.getChip
import com.sopt.cherish.util.extension.countNumberOfCharacters
import com.sopt.cherish.util.extension.shortToast
import com.sopt.cherish.util.extension.writeKeyword

// fragment로 바꿀까 고민중임
class ReviewFragment : Fragment() {

    private val TAG = "ReviewFragment"

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: ActivityReviewBinding =
            DataBindingUtil.inflate(inflater, R.layout.activity_review, container, false)

        initializeView(binding)
        addUserStatusWithChip(binding)
        addLimitNumberOfKeywordCharacters(binding)
        addLimitNumberOfMemoCharacters(binding)
        sendReviewToServer(binding)
        ignoreSendReviewToServer(binding)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun initializeView(binding: ActivityReviewBinding) {
        binding.reviewUser.text =
            "${viewModel.userNickName.value}님! ${viewModel.selectedCherishUser.value?.nickName}과/와의"
        binding.reviewDescription.text =
            "${viewModel.selectedCherishUser.value?.nickName}과/와의 물주기를 기록해주세요"
    }

    private fun addLimitNumberOfMemoCharacters(binding: ActivityReviewBinding) {
        binding.reviewMemo.countNumberOfCharacters { memo ->
            binding.reviewNumberOfMemo.text = memo?.length.toString()
            if (memo?.length!! > 100) {
                shortToast(requireContext(), "100자를 초과했습니다.")
            }
        }
    }

    private fun addLimitNumberOfKeywordCharacters(binding: ActivityReviewBinding) {
        binding.reviewEditKeyword.countNumberOfCharacters { keyword ->
            binding.reviewNumberOfCharacters.text = keyword?.length.toString()
            if (keyword?.length!! > 5) {
                val warningDialog = CustomDialogFragment(R.layout.dialog_keyword_limit_error)
                warningDialog.show(parentFragmentManager, TAG)
            }
        }
    }

    private fun showLoadingDialog() {
        Handler(Looper.getMainLooper()).postDelayed({
            val loadingDialog = CustomDialogFragment(R.layout.dialog_loading)
            loadingDialog.show(parentFragmentManager, TAG)
            loadingDialog.dismiss()
        }, 3000)
    }

    private fun addUserStatusWithChip(binding: ActivityReviewBinding) {
        // todo : 한글 키보드는 ENTER를 치게 되면 줄바꿈이 된다. 이거 처리를 해줘야 한다.
        // 이거 처리만 해주면 끝이 납니다
        // 글자수에 따라 엔터를 먹히지 않게 한다던지 하면 될거 같음
        // 다이얼로그가 왜 뜨는 지 모르겠는데 일단 뜸 ㅋㅋ
        binding.reviewEditKeyword.writeKeyword(binding.reviewFlexBox)
    }

    private fun sendReviewToServer(binding: ActivityReviewBinding) {
        binding.reviewAdminAccept.setOnClickListener {
            // 물주는 모션이 뜨면서 다시 main으로 넘어가면 됨
            // 리뷰를 전부 적지 않으면 안된다는 것이라던지에 대한 로직만 넣으면 해결됨
            viewModel.sendReviewToServer(
                reviewWateringReq = ReviewWateringReq(
                    binding.reviewMemo.text.toString(),
                    binding.reviewFlexBox.getChip(0)?.text.toString(),
                    binding.reviewFlexBox.getChip(1)?.text.toString(),
                    binding.reviewFlexBox.getChip(2)?.text.toString(),
                    viewModel.selectedCherishUser.value?.id!!
                )
            )
            viewModel.animationTrigger.value = true
            showLoadingDialog()
            navigateBeforePage()
        }
    }

    private fun ignoreSendReviewToServer(binding: ActivityReviewBinding) {
        binding.reviewIgnoreAccept.setOnClickListener {
            navigateBeforePage()
        }
    }

    private fun navigateBeforePage() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .remove(this)
            .commit()
    }
}