package com.sopt.cherish.ui.review

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivityReviewBinding
import com.sopt.cherish.di.Injection
import com.sopt.cherish.remote.api.ReviewWateringReq
import com.sopt.cherish.ui.dialog.CustomDialogFragment
import com.sopt.cherish.util.extension.FlexBoxExtension.getChip
import com.sopt.cherish.util.extension.countNumberOfCharacters
import com.sopt.cherish.util.extension.shortToast
import com.sopt.cherish.util.extension.writeReview

// todo : 식물카드에서 물 줄 경우 오는 데이터 값들을 확인하고 이를 뷰모델에 넣어놔야 함
// todo : boolean값을 mainViewModel에다가 보내줘야합니다. 어떻게 하면 보낼 수 있을 지 생각을 해볼까요????
class ReviewActivity : AppCompatActivity() {
    private val viewModel: ReviewViewModel by viewModels { Injection.provideReviewViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityReviewBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_review)
        initializeViewModelData()
        binding.reviewViewModel = viewModel

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
        viewModel.reviewText = "${viewModel.userNickname}님! ${viewModel.selectedCherishNickname}과의"
        viewModel.reviewSubText = "${viewModel.selectedCherishNickname}과/와의 물주기를 기록하세요."
    }

    private fun addLimitNumberOfMemoCharacters(binding: ActivityReviewBinding) {
        binding.reviewMemo.countNumberOfCharacters { memo ->
            binding.reviewNumberOfMemo.text = memo?.length.toString()
            if (memo?.length!! > 100) {
                // todo : dialog로 보여줘야 함
                shortToast(this, "100자를 초과했습니다.")
            }
        }
    }

    private fun addLimitNumberOfKeywordCharacters(binding: ActivityReviewBinding) {
        binding.reviewEditKeyword.countNumberOfCharacters { keyword ->
            binding.reviewNumberOfCharacters.text = keyword?.length.toString()
            if (keyword?.length!! > 5) {
                CustomDialogFragment(R.layout.sample_wordcount_error).show(
                    supportFragmentManager,
                    TAG
                )
            }
        }
    }

    private fun showLoadingDialog() {
        // todo : 로딩 다이얼로그가 제대로 보여지질 않음...
        CustomDialogFragment(R.layout.dialog_loading).show(supportFragmentManager, TAG)
        finish()
    }

    private fun addUserStatusWithChip(binding: ActivityReviewBinding) {
        binding.reviewEditKeyword.writeReview(binding.reviewFlexBox)
    }

    private fun sendReviewToServer(binding: ActivityReviewBinding) {
        binding.reviewAdminAccept.setOnClickListener {
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
            val intent = Intent()
            intent.putExtra("reviewCode", true)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun ignoreSendReviewToServer(binding: ActivityReviewBinding) {
        binding.reviewIgnoreAccept.setOnClickListener {
            // 일단 지금 당장은 그냥 finish만 시켜놨다
            // 현재의 값을 그냥 보내버리는 경우도 있을거야
            finish()
        }
    }

    companion object {
        val TAG = "ReviewActivity"
    }
}