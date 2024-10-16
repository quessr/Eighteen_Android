package com.eighteen.eighteenandroid.presentation.badgedetail

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.eighteen.eighteenandroid.databinding.FragmentBadgeDetailBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.eighteen.eighteenandroid.presentation.badgedetail.model.BadgeDetailModel
import com.eighteen.eighteenandroid.presentation.common.dp2Px
import com.eighteen.eighteenandroid.presentation.common.recyclerview.GridMarginItemDecoration
import com.eighteen.eighteenandroid.presentation.common.showDialogFragment

class BadgeDetailFragment :
    BaseFragment<FragmentBadgeDetailBinding>(FragmentBadgeDetailBinding::inflate) {
    override fun initView() {
        bind {
            ivBtnBack.setOnClickListener { findNavController().popBackStack() }
            rvBadges.run {
                itemAnimator = null
                layoutManager = GridLayoutManager(context, BADGES_SPAN_COUNT)
                adapter = BadgeDetailAdapter(onClickBadge = ::showBadgeDialogFragment).apply {
                    submitList(testData)
                }
                val horizontalBetweenMarginPx = context.dp2Px(HORIZONTAL_BETWEEN_MARGIN_DP)
                val verticalBetweenMarginPx = context.dp2Px(VERTICAL_BETWEEN_MARGIN_DP)
                val horizontalMarginPx = context.dp2Px(HORIZONTAL_MARGIN_DP)
                val verticalMarginPx = context.dp2Px(VERTICAL_MARGIN_DP)
                addItemDecoration(
                    GridMarginItemDecoration(
                        spanCount = BADGES_SPAN_COUNT,
                        horizontalBetweenMarginPx = horizontalBetweenMarginPx,
                        verticalBetweenMarginPx = verticalBetweenMarginPx,
                        startMarginPx = horizontalMarginPx,
                        endMarginPx = horizontalMarginPx,
                        topMarginPx = verticalMarginPx,
                        bottomMarginPx = verticalMarginPx
                    )
                )
            }
        }
    }

    private fun showBadgeDialogFragment(model: BadgeDetailModel) {
        val dialogFragment = BadgeDetailDialogFragment.newInstance(model = model)
        showDialogFragment(dialogFragment = dialogFragment)
    }

    companion object {
        private const val BADGES_SPAN_COUNT = 3
        private const val HORIZONTAL_BETWEEN_MARGIN_DP = 24
        private const val VERTICAL_BETWEEN_MARGIN_DP = 57
        private const val HORIZONTAL_MARGIN_DP = 14
        private const val VERTICAL_MARGIN_DP = 46
    }
}

//TODO test 데이터 제거
private val testData = List(100) {
    BadgeDetailModel(
        imageUrl = "https://picsum.photos/id/$it/200/300",
        badgeName = "name : $it",
        badgeDescription = "description :$it"
    )
}