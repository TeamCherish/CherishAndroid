package com.sopt.cherish.util.extension

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.countNumberOfCharacters(observeTextChanged: (CharSequence?) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            observeTextChanged(p0)
        }

        override fun afterTextChanged(p0: Editable?) {}
    })
}