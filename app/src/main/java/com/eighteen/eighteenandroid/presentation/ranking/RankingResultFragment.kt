package com.eighteen.eighteenandroid.presentation.ranking

import androidx.fragment.app.viewModels
import com.eighteen.eighteenandroid.databinding.FragmentRankingResultBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.eighteen.eighteenandroid.presentation.common.ModelState
import com.eighteen.eighteenandroid.presentation.common.collectInLifecycle
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

        initStateFlow()
    }

    private fun initStateFlow() {
        collectInLifecycle(viewModel.rankingListStateFlow) {
            when (it) {
                is ModelState.Loading -> {
                    //TODO 로딩 처리
                }
                is ModelState.Success -> {
                    rankingResultAdapter.submitList(it.data)
                }
                is ModelState.Error -> {
                    //TODO 에러처리
                }
                is ModelState.Empty -> {
                    //do nothing
                }
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