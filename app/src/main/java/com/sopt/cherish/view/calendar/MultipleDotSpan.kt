package com.sopt.cherish.view.calendar

import android.graphics.Canvas
import android.graphics.Paint
import android.text.style.LineBackgroundSpan
import com.sopt.cherish.util.PixelUtil.pixel

/**
 * Created on 01-06 by SSong-develop
 */
class MultipleDotSpan(
    private val radius: Float,
    private val color: Int
) : LineBackgroundSpan {
    override fun drawBackground(
        canvas: Canvas,
        paint: Paint,
        left: Int,
        right: Int,
        top: Int,
        baseline: Int,
        bottom: Int,
        charSequence: CharSequence,
        start: Int,
        end: Int,
        lineNum: Int
    ) {
        var leftMost = 0
        val circleX = ((left + right) / 2 - leftMost).toFloat()
        val circleY = bottom + radius + 30.pixel
        val circleRadius = 10f
        paint.color = color
        canvas.drawCircle(circleX, circleY, circleRadius, paint)
        leftMost += 20
    }

}