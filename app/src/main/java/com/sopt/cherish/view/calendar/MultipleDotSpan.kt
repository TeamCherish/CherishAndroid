package com.sopt.cherish.view.calendar

import android.graphics.Canvas
import android.graphics.Paint
import android.text.style.LineBackgroundSpan

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
        // 그리 좋은 코드 아님 수정해야함
        // todo : 기기 비율에 맞춘 값으로 표시를 해줘야 함
        var leftMost = 0
        val circleX = ((left + right) / 2 - leftMost).toFloat()
        val circleY = bottom + radius + 30
        val circleRadius = 10f
        paint.color = color
        canvas.drawCircle(circleX, circleY, circleRadius, paint)
        leftMost += 20
    }

}