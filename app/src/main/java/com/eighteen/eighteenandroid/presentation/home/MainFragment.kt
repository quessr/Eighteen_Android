package com.eighteen.eighteenandroid.presentation.home

import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.eighteen.eighteenandroid.presentation.common.createChip
import com.eighteen.eighteenandroid.presentation.common.setTagStyle
import com.eighteen.eighteenandroid.databinding.FragmentMainBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.eighteen.eighteenandroid.presentation.home.adapter.TeenAdapter
import com.eighteen.eighteenandroid.common.enums.Tag
import com.eighteen.eighteenandroid.presentation.common.showDialogFragment
import com.eighteen.eighteenandroid.presentation.dialog.ReportDialogFragment
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import com.eighteen.eighteenandroid.presentation.common.showReportDialog

/**
 *
 * @file MainFragment.kt
 * @date 05/08/2024
 * 메인화면
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
        requireContext().let { context ->
            val todayTeenAdapter = TeenAdapter(context = context, showDialog = {showReportDialog()})
            val anotherTeenAdapter = TeenAdapter(context = context, showDialog = {showReportDialog()})

            bind {
                vpTodayTeen.run {
                    clipToPadding = false
                    clipChildren = false
                    offscreenPageLimit = 1
                    this.adapter = todayTeenAdapter
                    registerOnPageChangeCallback(pageChangeCallback)
                }
                vpAnotherTeen.run {
                    clipToPadding = false
                    clipChildren = false
                    offscreenPageLimit = 1
                    setPadding(10, 10, 10, 10)
                    this.adapter = anotherTeenAdapter
                }
            }

            viewModel.userData.observe(viewLifecycleOwner) { userList ->
                val randomUserList = userList.shuffled() // 또 다른 틴 - 랜덤 유저 목록
                todayTeenAdapter.updateView(userList)
                anotherTeenAdapter.updateView(randomUserList)
            }
        }
    }

    private fun initChipGroup() {
        for (tag in Tag.values()) {
            val chip = createChip(requireContext(), tag.strValue)
            if (tag == Tag.ALL) { // 화면 최초 진입 시 전체 태그가 클릭된 상태여야함
                chip.setTagStyle(isBlackBackground = true)
                selectedChip = chip
            }
            chip.setOnClickListener { _ ->
                selectedChip?.setTagStyle(isBlackBackground = false)
                chip.setTagStyle(isBlackBackground = true)
                selectedChip = chip
            }
            bind {
                chipGroup.addView(chip)
            }
        }
    }

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            bind {
                if (position == 0) {
                    vpTodayTeen.setPadding(0, 0, 70, 0)
                } else {
                    vpTodayTeen.setPadding(70, 0, 70, 0)
                }
            }
        }
    }

    private fun showReportDialog() {
        showReportDialog(
            context = requireContext(),
            showDialog = { showDialogFragment(ReportDialogFragment()) }
        )
    }

}