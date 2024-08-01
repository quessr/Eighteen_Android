package com.eighteen.eighteenandroid.presentation.dialog.selectqna

import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.presentation.common.dp2Px

class SelectQnaDialogItemDecoration : ItemDecoration() {
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val context = parent.context
        val dividerPaint = Paint().apply {
            color = ContextCompat.getColor(context, R.color.divider)
        }
        val heightPx = context.dp2Px(DIVIDER_HEIGHT_DP)
        val left = parent.paddingStart.toFloat()
        for (i in 0 until parent.childCount - 1) {
            val child = parent.getChildAt(i)
            val right = (parent.width - child.paddingEnd).toFloat()
            val top = child.bottom.toFloat()
            val bottom = top + heightPx
            c.drawRect(left, top, right, bottom, dividerPaint)
        }
    }

    companion object {
        private const val DIVIDER_HEIGHT_DP = 1
    }
}