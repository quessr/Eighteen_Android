package com.eighteen.eighteenandroid.presentation.teen

import android.view.ViewGroup
import android.widget.LinearLayout
import com.eighteen.eighteenandroid.databinding.FragmentTeenMainBinding
import com.eighteen.eighteenandroid.domain.model.TeenType
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.eighteen.eighteenandroid.presentation.common.dp2Px
import com.google.android.material.tabs.TabLayoutMediator

class TeenMainFragment : BaseFragment<FragmentTeenMainBinding>(FragmentTeenMainBinding::inflate) {

    companion object {

    }

    override fun initView() {
        bind {
            val items: List<TeenType> = getTeenTypeItems()
            val pagerAdapter = TeenTypePagerAdapter(requireContext(), items)
            vpTeen.adapter = pagerAdapter

            TabLayoutMediator(tabLayout, vpTeen) { _, _ -> }.attach()

            val tabs = tabLayout.getChildAt(0) as ViewGroup

            for (i in 0 until tabs.childCount ) {
                val tab = tabs.getChildAt(i)
                val layoutParams = tab.layoutParams as LinearLayout.LayoutParams
                layoutParams.weight = 0f
                layoutParams.width = requireContext().dp2Px(44)

                if( i == 0 ) {
                    layoutParams.marginEnd = requireContext().dp2Px(2)
                } else {
                    layoutParams.marginStart = requireContext().dp2Px(2)
                }

                tab.layoutParams = layoutParams
                tabLayout.requestLayout()
            }
        }
    }

    private fun getTeenTypeItems(): List<TeenType> {
        return listOf(
            TeenType.MOST_VOTE_COUNT_TEEN,
            TeenType.AWARDS_TEEN,
            TeenType.WEEKLY_POPULAR_TEEN,
            TeenType.MONTHLY_POPULAR_TEEN,
            TeenType.RECENT_JOIN_TEEN,
            TeenType.SAME_SCHOOL_TEEN,
            TeenType.MY_CHOICE_TEEN,
            TeenType.MOST_BADGE_TEEN
        )
    }
}