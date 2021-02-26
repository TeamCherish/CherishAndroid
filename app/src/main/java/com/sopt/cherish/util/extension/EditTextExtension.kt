package com.sopt.cherish.util.extension

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent.*
import android.widget.EditText
import androidx.fragment.app.FragmentManager
import com.google.android.flexbox.FlexboxLayout
import com.sopt.cherish.R
import com.sopt.cherish.util.MultiViewDialog
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

fun EditText.writeKeyword(reviewFlexBoxLayout: FlexboxLayout, fragmentManager: FragmentManager) {
    this.setOnKeyListener { view, keyCode, keyEvent ->
        when (keyEvent.action) {
            ACTION_DOWN -> {
                if (keyCode == KEYCODE_ENTER && keyCode != KEYCODE_BACK) {
                    val et = view as EditText
                    val keyword = et.text.toString()
                    if (reviewFlexBoxLayout.getChipsCount() < 4)
                        reviewFlexBoxLayout.addChip(keyword)
                    else {
                        MultiViewDialog(R.layout.dialog_keyword_limit_error, 0.7f, 0.169f).show(
                            fragmentManager,
                            "editTextExtension"
                        )
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