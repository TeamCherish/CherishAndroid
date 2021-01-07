package com.sopt.cherish.ui.review

import android.os.Bundle
import android.view.KeyEvent
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivityReviewBinding
import com.sopt.cherish.ui.dialog.CustomDialogFragment
import com.sopt.cherish.ui.main.MainViewModel
import com.sopt.cherish.util.extension.FlexBoxExtension.addChip
import com.sopt.cherish.util.extension.FlexBoxExtension.getChipsCount
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created On 01-05 by SSong-develop
 * data 이동은 liveData를 사용해서 할 예정
 */
class ReviewActivity : AppCompatActivity() {

    private val TAG = "ReviewActivity"

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityReviewBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_review)
        binding.mainViewModel = viewModel

        addUserStatusWithChip(binding)
        sendReviewToServer(binding)
        ignoreSendReviewToServer(binding)
    }

    private fun showLoadingDialog() {
        lifecycleScope.launch(Dispatchers.IO) {
            CustomDialogFragment(R.layout.sample_lottie).show(supportFragmentManager, TAG)
            delay(2000)
            // 만약에 홈 프라그먼트에서 물 주는 애니메이션을 해야한다 그러면 finishActivity로 변경해야할 수도 있음
            finish()
        }
    }

    private fun addUserStatusWithChip(binding: ActivityReviewBinding) {
        // 한글 키보드는 ENTER를 치게 되면 줄바꿈이 된다. 이거 처리를 해줘야 한다.
        // 이거 처리만 해주면 끝이 납니다
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
            showLoadingDialog()
        }
    }

    private fun ignoreSendReviewToServer(binding: ActivityReviewBinding) {
        binding.reviewIgnoreAccept.setOnClickListener {
            // 건너 뛰는 것도 뭐~ 그냥 main으로 넘어오면 됨
            finish()
        }
    }

}