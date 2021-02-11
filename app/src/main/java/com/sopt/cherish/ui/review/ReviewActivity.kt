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
import com.sopt.cherish.ui.dialog.CustomDialogFragment
import com.sopt.cherish.util.SimpleLogger
import com.sopt.cherish.util.extension.FlexBoxExtension.addChip
import com.sopt.cherish.util.extension.FlexBoxExtension.getChip
import com.sopt.cherish.util.extension.FlexBoxExtension.getChipsCount
import com.sopt.cherish.util.extension.countNumberOfCharacters
import com.sopt.cherish.util.extension.shortToast
import java.util.*

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
        // todo : 한글 키보드는 ENTER를 치게 되면 줄바꿈이 된다. 이거 처리를 해줘야 한다.
        // todo : 확장 함수로 처리를 해서 좀 더 코드가 더럽지 않게 작업하자
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
            // todo : chip 하나가 씹힘
            viewModel.sendReviewToServer(
                reviewWateringReq = ReviewWateringReq(
                    binding.reviewMemo.text.toString(),
                    binding.reviewFlexBox.getChip(id = 0)?.text.toString(),
                    binding.reviewFlexBox.getChip(id = 1)?.text.toString(),
                    binding.reviewFlexBox.getChip(id = 2)?.text.toString(),
                    viewModel.selectedCherishId
                )
            )
            SimpleLogger.logI(binding.reviewFlexBox.getChip(id = 0)?.text.toString())
            SimpleLogger.logI(binding.reviewFlexBox.getChip(id = 1)?.text.toString())
            SimpleLogger.logI(binding.reviewFlexBox.getChip(id = 2)?.text.toString())
            SimpleLogger.logI(binding.reviewMemo.text.toString())
            showLoadingDialog()
            finish()
        }
    }

    private fun ignoreSendReviewToServer(binding: ActivityReviewBinding) {
        binding.reviewIgnoreAccept.setOnClickListener {
            // 일단 지금 당장은 그냥 finish만 시켜놨다
            //
            finish()
        }
    }

    companion object {
        val TAG = "ReviewActivity"
    }
}