package com.eighteen.eighteenandroid.presentation.home

import android.view.ViewGroup.LayoutParams
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentMainBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.eighteen.eighteenandroid.presentation.home.adapter.TeenAdapter
import com.eighteen.eighteenandroid.presentation.home.util.enums.Tag
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

/**
 *
 * @file MainFragment.kt
 * @date 05/08/2024
 */
@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    private val viewModel by viewModels<MainViewModel>()

    private var selectedChip: Chip? = null

    override fun initView() {
        initViewPager()
        initChipGroup()
    }

    private fun initViewPager() {
        val adapter = TeenAdapter(requireContext())

        bind {
            vpTodayTeen.run {
                clipToPadding = false
                clipChildren = false
                offscreenPageLimit = 1
                setPadding(0, 0, 70, 0)
                this.adapter = adapter
            }
        }

        viewModel.userData.observe(viewLifecycleOwner) { userList ->
            adapter.updateView(userList)
        }
    }

    private fun initChipGroup() {
        for (tag in Tag.values()) {
            val chip = createChip(tag.strValue)
            if (tag == Tag.ALL) { // 화면 최초 진입 시 전체 태그가 클릭된 상태여야함
                setChipStyle(chip, isBlackBackground = true)
                selectedChip = chip
            }
            chip.setOnClickListener { _ ->
                selectedChip?.let { it ->
                    setChipStyle(it, isBlackBackground = false)
                }
                setChipStyle(chip, isBlackBackground = true)
                selectedChip = chip
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

    private fun setChipStyle(chip: Chip, isBlackBackground: Boolean) {
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
}