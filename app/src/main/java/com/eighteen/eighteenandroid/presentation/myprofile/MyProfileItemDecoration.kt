package com.eighteen.eighteenandroid.presentation.myprofile

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.presentation.common.dp2Px

class MyProfileItemDecoration : ItemDecoration() {
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val dividerHeightPx = parent.context.dp2Px(DIVIDER_HEIGHT_DP)
        val paint = Paint().apply {
            color = ContextCompat.getColor(parent.context, R.color.white_grey)
        }
        parent.children.forEach {
            val adapterPosition = parent.getChildAdapterPosition(it)
            if (adapterPosition + 1 == parent.adapter?.itemCount) return@forEach
            val top = it.bottom.toFloat()
            c.drawRect(0f, top, it.width.toFloat(), top + dividerHeightPx.toFloat(), paint)
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val adapterPosition = parent.getChildAdapterPosition(view)
        if (adapterPosition + 1 == parent.adapter?.itemCount) return
        val dividerHeightPx = view.context.dp2Px(DIVIDER_HEIGHT_DP)
        outRect.bottom = dividerHeightPx
    }

    companion object {
        private const val DIVIDER_HEIGHT_DP = 7
    }
}