package com.sopt.cherish.util.extension

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent.*
import android.widget.EditText
import com.google.android.flexbox.FlexboxLayout
import com.sopt.cherish.util.extension.FlexBoxExtension.addChip
import com.sopt.cherish.util.extension.FlexBoxExtension.getChipsCount

fun EditText.countNumberOfCharacters(observeTextChanged: (CharSequence?) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            observeTextChanged(p0)
        }

        override fun afterTextChanged(p0: Editable?) {}
    })
}

fun EditText.writeReview(reviewFlexBoxLayout: FlexboxLayout) {
    this.setOnKeyListener { view, keyCode, keyEvent ->
        // 이 문구를 뭔가 좀 코틀린스럽게 짤수 있으면 좋겠다. 훈기야
        when (keyEvent.action) {
            ACTION_DOWN -> {
                if (keyCode == KEYCODE_ENTER && keyCode != KEYCODE_BACK) {
                    val et = view as EditText
                    val keyword = et.text.toString()
                    if (reviewFlexBoxLayout.getChipsCount() < 4)
                        reviewFlexBoxLayout.addChip(keyword)
                    else {
                        shortToast(view.context, "키워드는 최대 3개까지만 가능합니다.")
                        this.hideKeyboard()
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