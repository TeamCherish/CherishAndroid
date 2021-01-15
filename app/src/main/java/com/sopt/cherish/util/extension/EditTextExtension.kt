package com.sopt.cherish.util.extension

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
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
        if (keyEvent.action == KeyEvent.ACTION_DOWN || keyCode == KeyEvent.KEYCODE_ENTER) {
            val et = view as EditText
            val name = et.text.toString()
            if (reviewFlexBoxLayout.getChipsCount() < 3)
                reviewFlexBoxLayout.addChip(name)
            else {
                this.hideKeyboard()
            }
            et.text = null
        }
        return@setOnKeyListener false
    }
}