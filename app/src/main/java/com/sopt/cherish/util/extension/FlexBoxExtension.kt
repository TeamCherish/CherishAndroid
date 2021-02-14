package com.sopt.cherish.util.extension

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.chip.Chip
import com.sopt.cherish.R
import kotlin.math.roundToInt

object FlexBoxExtension {
    fun FlexboxLayout.addChip(text: String) {
        val chip = LayoutInflater.from(context).inflate(R.layout.view_chip_action, null) as Chip
        chip.text = text
        val layoutParams = ViewGroup.MarginLayoutParams(
            ViewGroup.MarginLayoutParams.WRAP_CONTENT,
            ViewGroup.MarginLayoutParams.WRAP_CONTENT
        )
        layoutParams.rightMargin = dpToPx(4)
        chip.setOnCloseIconClickListener { removeView(chip as View) }
        addView(chip, childCount - 1, layoutParams)
    }

    fun FlexboxLayout.addChipCalendar(text: String) {
        val chip = LayoutInflater.from(context).inflate(R.layout.view_chip_choice, null) as Chip
        chip.text = text
        val layoutParams = ViewGroup.MarginLayoutParams(
            ViewGroup.MarginLayoutParams.WRAP_CONTENT,
            ViewGroup.MarginLayoutParams.WRAP_CONTENT
        )
        layoutParams.rightMargin = dpToPx(4)
        chip.setOnCloseIconClickListener { removeView(chip as View) }
        addView(chip, childCount - 1, layoutParams)
    }

    fun FlexboxLayout.getAllChips(): List<Chip> {
        return (0 until childCount).mapNotNull { index ->
            getChildAt(index) as? Chip
        }
    }

    fun FlexboxLayout.getChip(id: Int): Chip? {
        return if (getChildAt(id) == null) {
            null
        } else {
            getChildAt(id) as Chip
        }
    }

    // todo : 이렇게 처리를 해도 되는건지 의문이긴 함 로직에 문제가 있는지 다시한번 생각
    fun FlexboxLayout.getChipsCount(): Int = childCount + 1

    fun FlexboxLayout.clearChips() {
        val chipViews = (0 until childCount).mapNotNull { index ->
            val view = getChildAt(index)
            if (view is Chip) view else null
        }
        chipViews.forEach { removeView(it) }
    }

    private fun FlexboxLayout.dpToPx(dp: Int): Int =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            resources.displayMetrics
        ).roundToInt()

}