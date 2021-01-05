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
        binding.reviewEditKeyword.setOnKeyListener { view, keyCode, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN || keyCode == KeyEvent.KEYCODE_ENTER) {
                val et = view as EditText
                val name = et.text.toString()
                binding.reviewFlexBox.addChip(name)
                et.text = null
            }
            return@setOnKeyListener false
        }

        binding.reviewAdminAccept.setOnClickListener {
            // 물주는 모션이 뜨면서 다시 main으로 넘어가면 됨
            // 리뷰를 전부 적지 않으면 안된다는 것이라던지에 대한 로직만 넣으면 해결됨
            showLoadingDialog()
        }

        binding.reviewIgnoreAccept.setOnClickListener {
            // 건너 뛰는 것도 뭐~ 그냥 main으로 넘어오면 됨
            finish()
        }
    }

    private fun showLoadingDialog() {
        lifecycleScope.launch(Dispatchers.IO) {
            CustomDialogFragment(R.layout.sample_lottie).show(supportFragmentManager, "TAG")
            delay(2000)
            finish()
        }
    }
}