package com.eighteen.eighteenandroid.presentation.ranking

import com.eighteen.eighteenandroid.common.enums.Tag
import com.eighteen.eighteenandroid.databinding.FragmentRankingBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.eighteen.eighteenandroid.presentation.common.createChip
import com.eighteen.eighteenandroid.presentation.common.setTagStyle
import com.eighteen.eighteenandroid.presentation.ranking.cardList.model.CardListItem
import com.eighteen.eighteenandroid.presentation.ranking.model.RankingCategory
import com.google.android.material.chip.Chip

class RankingFragment : BaseFragment<FragmentRankingBinding>(FragmentRankingBinding::inflate) {
    private var selectedChip: Chip? = null
    override fun initView() {
        val adapter = RankingAdapter()
        initChipGroup()


        binding.rvRanking.run {
            this.adapter = adapter
        }

        val categories = listOf(
            RankingCategory.Category(
                id = "1",
                categoryTitle = "뷰티",
                cardListItems = listOf(
                    CardListItem.VoteCard(
                        id = "vote_1",
                        category = "Beauty",
                        illustrationUrl = "https://contents-cdn.viewus.co.kr/image/2023/12/CP-2022-0017/image-de4d5a79-bbe3-4c2e-84a7-f36976345663.jpeg"
                    ),
                    CardListItem.WinnerCard(
                        id = "winner_1",
                        category = "Beauty",
                        imageUrl = "https://cdn.seoulwire.com/news/photo/202109/450631_649892_1740.jpg",
                        title = "1회차 우승자",
                        tournamentNumb = 1
                    ),
                    CardListItem.WinnerCard(
                        id = "winner_2",
                        category = "Beauty",
                        imageUrl = "https://cdn.seoulwire.com/news/photo/202109/450631_649892_1740.jpg",
                        title = "2회차 우승자",
                        tournamentNumb = 2
                    ),
                    CardListItem.WinnerCard(
                        id = "winner_2",
                        category = "Beauty",
                        imageUrl = "https://cdn.seoulwire.com/news/photo/202109/450631_649892_1740.jpg",
                        title = "3회차 우승자",
                        tournamentNumb = 2
                    ),
                    CardListItem.WinnerCard(
                        id = "winner_2",
                        category = "Beauty",
                        imageUrl = "https://cdn.seoulwire.com/news/photo/202109/450631_649892_1740.jpg",
                        title = "4회차 우승자",
                        tournamentNumb = 2
                    )
                )
            ),
            RankingCategory.Category(
                id = "1",
                categoryTitle = "운동",
                cardListItems = listOf(
                    CardListItem.VoteCard(
                        id = "vote_1",
                        category = "Beauty",
                        illustrationUrl = "https://contents-cdn.viewus.co.kr/image/2023/12/CP-2022-0017/image-de4d5a79-bbe3-4c2e-84a7-f36976345663.jpeg"
                    ),
                    CardListItem.WinnerCard(
                        id = "winner_1",
                        category = "Beauty",
                        imageUrl = "https://cdn.seoulwire.com/news/photo/202109/450631_649892_1740.jpg",
                        title = "1회차 우승자",
                        tournamentNumb = 1
                    ),
                    CardListItem.WinnerCard(
                        id = "winner_2",
                        category = "Beauty",
                        imageUrl = "https://cdn.seoulwire.com/news/photo/202109/450631_649892_1740.jpg",
                        title = "2회차 우승자",
                        tournamentNumb = 2
                    ),
                    CardListItem.WinnerCard(
                        id = "winner_2",
                        category = "Beauty",
                        imageUrl = "https://cdn.seoulwire.com/news/photo/202109/450631_649892_1740.jpg",
                        title = "3회차 우승자",
                        tournamentNumb = 2
                    ),
                    CardListItem.WinnerCard(
                        id = "winner_2",
                        category = "Beauty",
                        imageUrl = "https://cdn.seoulwire.com/news/photo/202109/450631_649892_1740.jpg",
                        title = "4회차 우승자",
                        tournamentNumb = 2
                    )
                )
            )
        )

        // 데이터를 어댑터에 설정
        adapter.submitList(categories)
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
}