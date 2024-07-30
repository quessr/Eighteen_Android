package com.eighteen.eighteenandroid.presentation.myprofile.edittenofqna

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.eighteen.eighteenandroid.presentation.common.dp2Px
import com.eighteen.eighteenandroid.presentation.myprofile.edittenofqna.model.EditTenOfQnaModel

class EditTenOfQnaItemDecoration : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val adapter = (parent.adapter as? EditTenOfQnaAdapter) ?: return
        val adapterPosition = parent.getChildAdapterPosition(view)
        val currentModel = adapter.currentList[adapterPosition]
        val prevModel = adapter.currentList.getOrNull(adapterPosition - 1)
        val context = parent.context
        when (currentModel) {
            is EditTenOfQnaModel.Input -> {
                prevModel?.let {
                    val topOffset = getTopOffsetInputItem(context = context, prev = it)
                    outRect.top = topOffset
                }
            }
            is EditTenOfQnaModel.Add -> {
                prevModel?.let {
                    val topOffset = getTopOffsetAddItem(context = context, prev = it)
                    outRect.top = topOffset
                }
            }
            else -> {
                //do nothing
            }
        }
    }

    private fun getTopOffsetInputItem(
        context: Context,
        prev: EditTenOfQnaModel
    ): Int {
        val offsetDp = when (prev) {
            is EditTenOfQnaModel.Title -> 42
            is EditTenOfQnaModel.Input -> 10
            else -> 0
        }
        return context.dp2Px(offsetDp)
    }

    private fun getTopOffsetAddItem(context: Context, prev: EditTenOfQnaModel): Int {
        val offsetDp = when (prev) {
            is EditTenOfQnaModel.Title -> 42
            is EditTenOfQnaModel.Input -> 35
            else -> 0
        }
        return context.dp2Px(offsetDp)
    }
}

