package com.sopt.cherish.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridItemDecorator(
    private val spanCount: Int,
    private val spacing: Int,
    private val includeEdge: Boolean
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount // item column

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount - 12
            outRect.right = (column + 1) * spacing / spanCount - 12

            // 만약 디자인 팀에서 위에 간격을 좀 줬으면 좋겠다 하면 사용
            /*if (position < spanCount) // top Edge
                outRect.top = spacing*/

            // 너무 먼느낌이라서 이건 디자인에게 물어봐서 값 수정하면 됨!!
            // itemDecorator에 marginBottom값 줘서 그 값으로 해주면 됨
            outRect.bottom = spacing
        } else {
            outRect.left = column * spacing / spanCount - 12
            outRect.right = spacing - (column + 1) * spacing / spanCount - 12
            /* if (position >= spanCount)
                 outRect.top = spacing*/
        }
    }
}