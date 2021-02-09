package com.sopt.cherish.ui.review

import android.annotation.SuppressLint
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
import com.sopt.cherish.ui.dialog.CustomDialogFragment
import com.sopt.cherish.util.extension.FlexBoxExtension.addChip
import com.sopt.cherish.util.extension.FlexBoxExtension.getChip
import com.sopt.cherish.util.extension.FlexBoxExtension.getChipsCount
import com.sopt.cherish.util.extension.countNumberOfCharacters
import com.sopt.cherish.util.extension.shortToast
import java.util.*

class ReviewActivity : AppCompatActivity() {
    // viewModel을 값을 ContactDialog에서 받아서 사용한다 이때 받는건 parcelable로 해서 하면 될거 같다 다시 한번 생각해보라
    private val viewModel: ReviewViewModel by viewModels { Injection.provideReviewViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityReviewBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_review)
        initializeViewModel()
        binding.reviewViewModel = viewModel

        initializeView(binding)
        addUserStatusWithChip(binding)
        addLimitNumberOfKeywordCharacters(binding)
        addLimitNumberOfMemoCharacters(binding)
        sendReviewToServer(binding)
        ignoreSendReviewToServer(binding)
    }

    private fun initializeViewModel() {
        viewModel.userNickname = intent.getStringExtra("userNickname")!!
        viewModel.selectedCherishNickname = intent.getStringExtra("selectedCherishNickname")!!
        viewModel.selectedCherishId = intent.getIntExtra("selectedCherishId", 0)
        viewModel.reviewText = "${viewModel.userNickname}님! ${viewModel.selectedCherishNickname}과의"
        viewModel.reviewSubText = "${viewModel.selectedCherishNickname}과의 물주기를 기록하세요."
    }

    @SuppressLint("SetTextI18n")
    private fun initializeView(binding: ActivityReviewBinding) {
        // 야매로함 , 근데 이거 안됨
        binding.reviewUser.text =
            "${intent.getStringExtra("userNickname")}님! ${intent.getStringExtra("cherishNickname")}님과의"
        binding.reviewDescription.text =
            "${intent.getStringExtra("cherishNickname")}님과의 물주기를 기록해주세요"
    }

    private fun addLimitNumberOfMemoCharacters(binding: ActivityReviewBinding) {
        binding.reviewMemo.countNumberOfCharacters { memo ->
            binding.reviewNumberOfMemo.text = memo?.length.toString()
            if (memo?.length!! > 100) {
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
        CustomDialogFragment(R.layout.dialog_loading).show(supportFragmentManager, TAG)
        finish()
    }

    private fun addUserStatusWithChip(binding: ActivityReviewBinding) {
        // todo : 한글 키보드는 ENTER를 치게 되면 줄바꿈이 된다. 이거 처리를 해줘야 한다.
        // 이거 처리만 해주면 끝이 납니다
        // 글자수에 따라 엔터를 먹히지 않게 한다던지 하면 될거 같음
        binding.reviewEditKeyword.setOnKeyListener { view, keyCode, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN || keyCode == KeyEvent.KEYCODE_ENTER) {
                val et = view as EditText
                val name = et.text.toString()
                if (binding.reviewFlexBox.getChipsCount() < 3)
                    binding.reviewFlexBox.addChip(name)
                else {
                    CustomDialogFragment(R.layout.sample_lottie2).show(supportFragmentManager, TAG)
                }
                et.text = null
            }
            return@setOnKeyListener false
        }
    }

    private fun sendReviewToServer(binding: ActivityReviewBinding) {
        binding.reviewAdminAccept.setOnClickListener {
            // 물주는 모션이 뜨면서 다시 main으로 넘어가면 됨
            // 리뷰를 전부 적지 않으면 안된다는 것이라던지에 대한 로직만 넣으면 해결됨
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
            finish()
        }
    }

    private fun ignoreSendReviewToServer(binding: ActivityReviewBinding) {
        binding.reviewIgnoreAccept.setOnClickListener {
            // 건너 뛰는 것도 뭐~ 그냥 main으로 넘어오면 됨
            finish()
        }
    }

    companion object {
        val TAG = "ReviewActivity"
    }
}