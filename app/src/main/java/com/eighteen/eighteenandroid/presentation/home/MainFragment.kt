package com.eighteen.eighteenandroid.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentMainBinding
import com.google.android.material.chip.Chip

/**
 *
 * @file MainFragment.kt
 * @date 05/08/2024
 */
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPager()
        initChipGroup()
    }

    private fun initViewPager() {
        val teenList: MutableList<String> = mutableListOf()

        teenList.add("Kim Young Hoon")
        teenList.add("Heo Se Ra")
        teenList.add("Jeong Minji Ji")

        val adapter = ViewPagerAdapter(teenList)
        binding.vpTodayTeen.apply {
            this.adapter = adapter
            this.clipToPadding = false
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
                    chip.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
                } else {
                    chip.setChipBackgroundColorResource(android.R.color.white)
                    chip.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
                }
            }
            binding.chipGroup.addView(chip)
        }
    }

    private fun createChip(tag: String): Chip {
        val chip = layoutInflater.inflate(R.layout.tag_item, null) as Chip
        chip.text = tag
        chip.setChipBackgroundColorResource(android.R.color.white)
        return chip
    }
}