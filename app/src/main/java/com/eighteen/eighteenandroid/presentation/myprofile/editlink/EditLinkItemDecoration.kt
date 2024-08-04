package com.eighteen.eighteenandroid.presentation.myprofile.editlink

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.eighteen.eighteenandroid.presentation.common.dp2Px

class EditLinkItemDecoration : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val adapterPosition = parent.getChildAdapterPosition(view)
        if (adapterPosition == 0) return
        outRect.top = view.context.dp2Px(10)
    }
}