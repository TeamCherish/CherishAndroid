package com.sopt.cherish.view.edittext

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import com.sopt.cherish.R

class ClearEditText : AppCompatEditText {

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    private val blackClearDrawable =
        ResourcesCompat.getDrawable(resources, R.drawable.ic_clear_black_24dp, null) as Drawable
    private val opaqueClearDrawable =
        ResourcesCompat.getDrawable(resources, R.drawable.ic_clear_opaque_24dp, null) as Drawable

    private var clearButtonImage: Drawable = opaqueClearDrawable

    private fun showClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, clearButtonImage, null)
    }

    private fun hideClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (isClearButtonVisible() && wasClearButtonTouched(event)) {
            onClearButtonTouched(event)
            return true
        }

        return super.onTouchEvent(event)
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        if (text?.isNotEmpty() == true) {
            showClearButton()
        } else {
            hideClearButton()
        }
    }

    private fun isClearButtonVisible(): Boolean {
        return compoundDrawablesRelative[2] != null
    }

    private fun wasClearButtonTouched(event: MotionEvent): Boolean {
        val isClearButtonAtTheStart = layoutDirection == View.LAYOUT_DIRECTION_RTL

        return if (isClearButtonAtTheStart) {

            val clearButtonEnd = paddingStart + clearButtonImage.intrinsicWidth
            event.x < clearButtonEnd

        } else {

            val clearButtonStart = width - clearButtonImage.intrinsicWidth - paddingEnd
            event.x > clearButtonStart

        }
    }

    private fun onClearButtonTouched(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                clearButtonImage = blackClearDrawable
                showClearButton()
            }
            MotionEvent.ACTION_UP -> {
                clearButtonImage = opaqueClearDrawable
                text?.clear()
                hideClearButton()
            }
        }
    }

}