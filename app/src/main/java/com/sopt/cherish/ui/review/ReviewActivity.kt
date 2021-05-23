package com.sopt.cherish.ui.review

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivityReviewBinding
import com.sopt.cherish.di.Injection
import com.sopt.cherish.remote.api.ReviewWateringReq
import com.sopt.cherish.util.MultiViewDialog
import com.sopt.cherish.util.SimpleLogger
import com.sopt.cherish.util.extension.FlexBoxExtension.getChip
import com.sopt.cherish.util.extension.countNumberOfCharacters
import com.sopt.cherish.util.extension.shortToast
import com.sopt.cherish.util.extension.writeKeyword
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ReviewActivity : AppCompatActivity() {
    private val viewModel: ReviewViewModel by viewModels { Injection.provideReviewViewModelFactory() }

    private val reviewNotificationViewModel by lazy {
        ViewModelProvider(this).get(ReviewNotificationViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityReviewBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_review)
        initializeViewModelData()
        binding.reviewViewModel = viewModel
        binding.lifecycleOwner = this
        reviewNotificationViewModel.startNotificationTimer()
        observeErrorHandleLiveData()
        addUserStatusWithChip(binding)
        addLimitNumberOfKeywordCharacters(binding)
        addLimitNumberOfMemoCharacters(binding)
        sendReviewToServer(binding)
        ignoreSendReviewToServer(binding)
    }

    private fun observeErrorHandleLiveData() {
        viewModel.errorHandleLiveData.observe(this) {
            shortToast(this, "네트워크 상태를 확인해주세요")
        }
    }

    private fun initializeViewModelData() {
        viewModel.userNickname = intent.getStringExtra("userNickname")!!
        viewModel.selectedCherishNickname = intent.getStringExtra("selectedCherishNickname")!!
        viewModel.selectedCherishId = intent.getIntExtra("selectedCherishId", 0)
        viewModel.reviewText =
            "${viewModel.userNickname}님! ${viewModel.selectedCherishNickname}님와(과)의"
        viewModel.reviewSubText = "${viewModel.selectedCherishNickname}와(과)의 물주기를 기록하세요."

        SimpleLogger.logI("${viewModel.userNickname},${viewModel.selectedCherishNickname},${viewModel.selectedCherishId}")
    }

    private fun addLimitNumberOfMemoCharacters(binding: ActivityReviewBinding) {
        binding.reviewMemo.countNumberOfCharacters { memo ->
            binding.reviewNumberOfMemo.text = memo?.length.toString()
        }
    }

    private fun addLimitNumberOfKeywordCharacters(binding: ActivityReviewBinding) {
        binding.reviewEditKeyword.countNumberOfCharacters { keyword ->
            binding.reviewNumberOfCharacters.text = keyword?.length.toString()
        }
    }

    private fun showLoadingDialog() {
        lifecycleScope.launch {
            val dialog = MultiViewDialog(R.layout.dialog_loading, 0.35f, 0.169f)
            dialog.show(supportFragmentManager, TAG)
            delay(2000)
            dialog.dismiss()
            val intent = Intent()
            intent.putExtra("wateringTrigger", true)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun addUserStatusWithChip(binding: ActivityReviewBinding) {
        binding.reviewEditKeyword.writeKeyword(binding.reviewFlexBox, supportFragmentManager)
    }

    private fun sendReviewToServer(binding: ActivityReviewBinding) {
        binding.reviewAdminAccept.setOnClickListener {
            if (binding.reviewMemo.text.length <= 100) {
                viewModel.sendReviewToServer(
                    reviewWateringReq = ReviewWateringReq(
                        binding.reviewMemo.text.toString(),
                        if (binding.reviewFlexBox.getChip(0) == null) "" else binding.reviewFlexBox.getChip(
                            0
                        )!!.text.toString(),
                        if (binding.reviewFlexBox.getChip(1) == null) "" else binding.reviewFlexBox.getChip(
                            1
                        )!!.text.toString(),
                        if (binding.reviewFlexBox.getChip(2) == null) "" else binding.reviewFlexBox.getChip(
                            2
                        )!!.text.toString(),
                        viewModel.selectedCherishId
                    )
                )
                showLoadingDialog()
            } else {
                MultiViewDialog(
                    R.layout.dialog_warning_review_limit_error,
                    0.6944f,
                    0.16875f
                ).show(
                    supportFragmentManager,
                    TAG
                )
            }
        }
        reviewNotificationViewModel.cancel()
    }

    private fun ignoreSendReviewToServer(binding: ActivityReviewBinding) {
        binding.reviewIgnoreAccept.setOnClickListener {
            viewModel.sendReviewToServer(
                reviewWateringReq = ReviewWateringReq(
                    null,
                    null,
                    null,
                    null,
                    viewModel.selectedCherishId
                )
            )
            showLoadingDialog()
        }
        reviewNotificationViewModel.cancel()
    }

    companion object {
        const val TAG = "ReviewActivity"
    }
}