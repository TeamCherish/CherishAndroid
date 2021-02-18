package com.sopt.cherish.ui.review

import android.os.Bundle
import android.view.KeyEvent
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivityReviewBinding
import com.sopt.cherish.di.Injection
import com.sopt.cherish.remote.api.ReviewWateringReq
import com.sopt.cherish.util.MultiViewDialog
import com.sopt.cherish.util.extension.*
import com.sopt.cherish.util.extension.FlexBoxExtension.addChip
import com.sopt.cherish.util.extension.FlexBoxExtension.getChip
import com.sopt.cherish.util.extension.FlexBoxExtension.getChipsCount
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
            if (memo?.length!! >= 100) {
                MultiViewDialog(R.layout.dialog_review_limit_error, 0.6f, 0.5f)
                binding.reviewMemo.hideKeyboard()
            }
        }
    }

    private fun addLimitNumberOfKeywordCharacters(binding: ActivityReviewBinding) {
        binding.reviewEditKeyword.countNumberOfCharacters { keyword ->
            binding.reviewNumberOfCharacters.text = keyword?.length.toString()
            if (keyword?.length!! >= 5) {
                binding.reviewEditKeyword.hideKeyboard()
            }
        }
    }

    // todo : extension 함수로 만들어주면 굉장히 좋을거 같음
    private fun showLoadingDialog() {
        CoroutineScope(Main).launch {
            // 다이얼로그 사이즈만 하면 됨
            val dialog = MultiViewDialog(R.layout.dialog_loading, 0.6f, 0.3f)
            dialog.show(supportFragmentManager, TAG)
            delay(2000)
            dialog.dismiss()
            finish()
        }
    }

    private fun addUserStatusWithChip(binding: ActivityReviewBinding) {
        binding.reviewEditKeyword.setOnKeyListener { view, keyCode, keyEvent ->
            when (keyEvent.action) {
                KeyEvent.ACTION_DOWN -> {
                    if (keyCode == KeyEvent.KEYCODE_ENTER && keyCode != KeyEvent.KEYCODE_BACK) {
                        val et = view as EditText
                        val keyword = et.text.toString()
                        if (binding.reviewFlexBox.getChipsCount() < 4) {
                            binding.reviewFlexBox.addChip(keyword)
                        } else {
                            MultiViewDialog(R.layout.dialog_keyword_limit_error, 0.6f, 0.5f).show(
                                supportFragmentManager,
                                TAG
                            )
                            binding.reviewEditKeyword.hideKeyboard()
                        }
                        et.text = null
                    }
                    return@setOnKeyListener false
                }
                else -> {
                    return@setOnKeyListener false
                }
            }
        }
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
        }
    }

    private fun ignoreSendReviewToServer(binding: ActivityReviewBinding) {
        binding.reviewIgnoreAccept.setOnClickListener {
            finish()
        }
    }

    companion object {
        val TAG = "ReviewActivity"
    }
}