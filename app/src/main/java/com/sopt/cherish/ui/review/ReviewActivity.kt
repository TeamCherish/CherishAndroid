package com.sopt.cherish.ui.review

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivityReviewBinding
import com.sopt.cherish.ui.main.MainViewModel
import com.sopt.cherish.util.extension.FlexBoxExtension.addChip

class ReviewActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityReviewBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_review)

        binding.reviewEditKeyword.setOnKeyListener { view, keyCode, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN || keyCode == KeyEvent.KEYCODE_ENTER) {
                val et = view as EditText
                val name = et.text.toString()
                Log.d("hehe", name)
                binding.reviewFlexBox.addChip(name)
                et.text = null
            }
            return@setOnKeyListener false
        }
        binding.mainViewModel = viewModel
    }
}