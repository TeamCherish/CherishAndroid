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
import com.sopt.cherish.util.extension.FlexBoxExtension.getChip
import com.sopt.cherish.util.extension.FlexBoxExtension.getChipsCount
import com.sopt.cherish.util.extension.countNumberOfCharacters
import com.sopt.cherish.util.extension.hideKeyboard
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
        addUserStatusWithChip(binding)
        addLimitNumberOfKeywordCharacters(binding)
        addLimitNumberOfMemoCharacters(binding)
        sendReviewToServer(binding)
        ignoreSendReviewToServer(binding)
    }

    private fun initializeViewModelData() {
        viewModel.userNickname = intent.getStringExtra("userNickname")!!
        viewModel.selectedCherishNickname = intent.getStringExtra("selectedCherishNickname")!!
        viewModel.selectedCherishId = intent.getIntExtra("selectedCherishId", 0)
        viewModel.reviewText =
            "${viewModel.userNickname}님! ${viewModel.selectedCherishNickname}님와(과)의"
        viewModel.reviewSubText = "${viewModel.selectedCherishNickname}와(과)의 물주기를 기록하세요."
    }

    private fun addLimitNumberOfMemoCharacters(binding: ActivityReviewBinding) {
        binding.reviewMemo.countNumberOfCharacters { memo ->
            binding.reviewNumberOfMemo.text = memo?.length.toString()
            if (memo?.length!! > 100) {
                MultiViewDialog(R.layout.dialog_warning_review_limit_error, 0.6944f, 0.16875f).show(
                    supportFragmentManager,
                    TAG
                )
                binding.reviewMemo.hideKeyboard()
            }
        }
    }

    private fun addLimitNumberOfKeywordCharacters(binding: ActivityReviewBinding) {
        binding.reviewEditKeyword.countNumberOfCharacters { keyword ->
            binding.reviewNumberOfCharacters.text = keyword?.length.toString()
            if (keyword?.length!! > 5) {
                MultiViewDialog(
                    R.layout.dialog_warning_keyword_wordcount_limit_error,
                    0.6944f,
                    0.16875f
                ).show(
                    supportFragmentManager,
                    TAG
                )
                binding.reviewEditKeyword.hideKeyboard()
            }
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
            if (binding.reviewFlexBox.getChipsCount() == 0 && binding.reviewMemo.text.isEmpty()) {
                MultiViewDialog(
                    R.layout.dialog_warning_review_no_word_warning,
                    0.6944f,
                    0.16875f
                ).show(
                    supportFragmentManager,
                    ReviewFragment.TAG
                )
            } else {
                if (binding.reviewMemo.text.length <= 100) {
                    viewModel.sendReviewToServer(
                        reviewWateringReq = ReviewWateringReq(
                            binding.reviewMemo.text.toString(),
                            binding.reviewFlexBox.getChip(id = 0)?.text.toString(),
                            binding.reviewFlexBox.getChip(id = 1)?.text.toString(),
                            binding.reviewFlexBox.getChip(id = 2)?.text.toString(),
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