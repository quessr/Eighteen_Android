package com.eighteen.eighteenandroid.presentation.auth.signup.selecttag

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.eighteen.eighteenandroid.presentation.common.dp2Px

class SignUpSelectTagItemDecoration : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val marginPx = parent.context.dp2Px(MARGIN_DP)
        val adapterPosition = parent.getChildAdapterPosition(view)
        if (adapterPosition == 0) outRect.left = marginPx
        outRect.right = marginPx
    }

    companion object {
        private const val MARGIN_DP = 16
    }
}