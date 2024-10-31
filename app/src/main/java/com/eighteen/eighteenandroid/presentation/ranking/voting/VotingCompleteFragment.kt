package com.eighteen.eighteenandroid.presentation.ranking.voting

import com.eighteen.eighteenandroid.databinding.FragmentVotingCompleteBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment

class VotingCompleteFragment :
    BaseFragment<FragmentVotingCompleteBinding>(FragmentVotingCompleteBinding::inflate) {
    override fun initView() {
        val winnerName = arguments?.getString("winnerName")
        val winnerId = arguments?.getString("winnerId")

        bind {
            tvName.text = winnerName
        }
    }
}