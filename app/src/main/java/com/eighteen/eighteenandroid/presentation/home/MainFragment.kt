package com.eighteen.eighteenandroid.presentation.home

import android.view.ViewGroup.LayoutParams
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentMainBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.google.android.material.chip.Chip

/**
 *
 * @file MainFragment.kt
 * @date 05/08/2024
 */
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    override fun initView() {
        initViewPager()
        initChipGroup()
    }

    private fun initViewPager() {
        val teenList: MutableList<String> = mutableListOf()

        teenList.add("https://image.blip.kr/v1/file/021ec61ff1c9936943383b84236a0e69")
        teenList.add("https://cdn.newsculture.press/news/photo/202308/529742_657577_5726.jpg")
        teenList.add("https://mblogthumb-phinf.pstatic.net/MjAyMTEwMzFfMTY1/MDAxNjM1NjUzMTI2NjI3.xXYQteLLoWLKcR9YnXS0Hk_y-DInauMzF25g7FxlcScg.2Y-neBBMVoP2IhcwzX2Zy2HB2d8EnM_cY76FVLuk_1Yg.JPEG.ssun2415/IMG_4148.jpg?type=w800")

        binding.vpTodayTeen.apply {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 1
            setPadding(0, 0, 70, 0)
            adapter = ViewPagerAdapter(requireContext(), teenList)
        }
    }

    private fun initChipGroup() {
        val tagList = listOf("전체", "운동", "외모")
        for (tag in tagList) {
            val chip = createChip(tag)
            var isBlackBackground = false
            chip.setOnClickListener { view ->
                isBlackBackground = !isBlackBackground
                if (isBlackBackground) {
                    chip.setChipBackgroundColorResource(android.R.color.black)
                    chip.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            android.R.color.white
                        )
                    )
                } else {
                    chip.setChipBackgroundColorResource(android.R.color.white)
                    chip.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            android.R.color.black
                        )
                    )
                }
            }
            bind {
                chipGroup.addView(chip)
            }
        }
    }

    private fun createChip(tag: String): Chip {
        val chip = layoutInflater.inflate(R.layout.tag_item, null) as Chip
        chip.text = tag
        chip.setChipBackgroundColorResource(android.R.color.white)

        // TODO: 추후 태그 문자열의 길이가 길어지는 경우도 대응해야함
        val params = LayoutParams(200, LayoutParams.WRAP_CONTENT)
        chip.layoutParams = params

        return chip
    }
}