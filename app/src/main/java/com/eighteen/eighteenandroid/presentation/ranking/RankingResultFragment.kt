package com.eighteen.eighteenandroid.presentation.ranking

import androidx.fragment.app.viewModels
import com.eighteen.eighteenandroid.databinding.FragmentRankingResultBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RankingResultFragment : BaseFragment<FragmentRankingResultBinding>(FragmentRankingResultBinding::inflate) {
    companion object {
        val ARGUMENT_TOURNAMENT_NO = "tournamentNo"
    }

    @Inject
    lateinit var rankingResultAssistedFactory: RankingResultViewModel.UserRankingAssistedFactory

    private lateinit var rankingResultAdapter: RankingResultAdapter

    private val viewModel: RankingResultViewModel by viewModels(factoryProducer = {
        RankingResultViewModel.Factory(
            assistedFactory = rankingResultAssistedFactory,
            tournamentNo = arguments?.getInt(ARGUMENT_TOURNAMENT_NO) ?: 1
        )
    })

    override fun initView() {
        initAdapter()

        bind {
            with(rvRankingResult) {
                adapter = rankingResultAdapter
            }
        }
    }

    private fun initAdapter() {
        rankingResultAdapter = RankingResultAdapter()
    }

    override fun onDestroyView() {
        viewModel.clear()
        super.onDestroyView()
    }
}