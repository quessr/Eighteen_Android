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
 * @param topMarginPx : 리스트 상단 마진 값(Px)
 * @param startMarginPx : 리스트 왼쪽 마진 값(Px)
 * @param bottomMarginPx : 리스트 하단 마진 값(Px)
 * @param endMarginPx : 리스트 오른쪽 마진 값(Px)

 */
class GridMarginItemDecoration(
    private val spanCount: Int,
    @Px private val horizontalBetweenMarginPx: Int,
    @Px private val verticalBetweenMarginPx: Int,
    @Px private val topMarginPx: Int = 0,
    @Px private val startMarginPx: Int = 0,
    @Px private val bottomMarginPx: Int = 0,
    @Px private val endMarginPx: Int = 0
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
            (horizontalBetweenMarginPx * (spanCount - 1) + startMarginPx + endMarginPx) / spanCount
        val leftOffset =
            if (column == 0) startMarginPx else column * (horizontalBetweenMarginPx - sumOfHorizontalMargins) + startMarginPx
        val rightOffset =
            if (column == spanCount - 1) endMarginPx else sumOfHorizontalMargins - leftOffset

        val sumOfVerticalMargins =
            (verticalBetweenMarginPx * (rowCount - 1) + topMarginPx + bottomMarginPx) / rowCount
        val topOffset =
            if (row == 0) topMarginPx else row * (verticalBetweenMarginPx - sumOfVerticalMargins) + topMarginPx
        val bottomOffset =
            if (row == rowCount - 1) bottomMarginPx else sumOfVerticalMargins - topOffset

        outRect.run {
            left = leftOffset
            right = rightOffset
            top = topOffset
            bottom = bottomOffset
        }
    }
}