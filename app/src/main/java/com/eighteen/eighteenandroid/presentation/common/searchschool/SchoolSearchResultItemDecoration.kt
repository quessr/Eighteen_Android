package com.eighteen.eighteenandroid.presentation.common.searchschool

import android.graphics.Canvas
import android.graphics.Paint
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class SchoolSearchResultItemDecoration(
    @ColorInt private val dividerColor: Int,
    @Px private val dividerHeightPx: Int,
) : ItemDecoration() {
    private val dividerColorPaint = Paint().apply {
        color = dividerColor
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingStart.toFloat()
        for (i in 0 until parent.childCount - 1) {
            val child = parent.getChildAt(i)
            val right = (parent.width - child.paddingEnd).toFloat()
            val top = child.bottom.toFloat()
            val bottom = top + dividerHeightPx
            c.drawRect(left, top, right, bottom, dividerColorPaint)
        }
    }
}