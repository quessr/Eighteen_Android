package com.eighteen.eighteenandroid.presentation.common.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * RecyclerView GridLayout Item 사이 간격 margin 할당
 * GridLayout 내 모든 Item의 사이에 적용
 * @param spanCount : GridLayoutManager의 spanCount
 * @param horizontalBetweenMarginPx : 가로 사이 마진 값(Px)
 * @param verticalBetweenMarginPx : 세로 사이 마진 값(Px)
 */
class GridMarginItemDecoration(
    private val spanCount: Int,
    @Px private val horizontalBetweenMarginPx: Int,
    @Px private val verticalBetweenMarginPx: Int
) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val itemCount = parent.adapter?.itemCount ?: return
        val rowCount = ((itemCount - 1) / spanCount) + 1
        val adapterPosition = parent.getChildAdapterPosition(view)
        val row = adapterPosition / spanCount
        val column = adapterPosition % spanCount
        val sumOfHorizontalMargins =
            horizontalBetweenMarginPx.toFloat() * (spanCount - 1) / spanCount
        val leftOffset = column * (horizontalBetweenMarginPx - sumOfHorizontalMargins)
        val rightOffset = sumOfHorizontalMargins - leftOffset

        val sumOfVerticalMargins = verticalBetweenMarginPx.toFloat() * (rowCount - 1) / rowCount
        val topOffset = row * (verticalBetweenMarginPx - sumOfVerticalMargins)
        val bottomOffset = sumOfVerticalMargins - topOffset

        outRect.run {
            left = leftOffset.toInt()
            right = rightOffset.toInt()
            top = topOffset.toInt()
            bottom = bottomOffset.toInt()
        }
    }
}