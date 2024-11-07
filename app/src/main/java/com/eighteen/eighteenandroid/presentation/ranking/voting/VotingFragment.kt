package com.eighteen.eighteenandroid.presentation.ranking.voting

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentVotingBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.eighteen.eighteenandroid.presentation.ranking.RankingFragment
import com.eighteen.eighteenandroid.presentation.ranking.RankingVotingDialogFragment
import com.eighteen.eighteenandroid.presentation.ranking.cardList.viewholder.CardListViewHolder
import com.eighteen.eighteenandroid.presentation.ranking.voting.model.TournamentEntity
import kotlinx.coroutines.launch

class VotingFragment : BaseFragment<FragmentVotingBinding>(FragmentVotingBinding::inflate) {
//    private lateinit var participants: List<TournamentEntity.Participant>
//    private var matches: List<TournamentEntity.Match> = listOf()
//    private var currentRound = 16
//    private var currentMatchIndex = 0

    private val viewModel: VotingViewModel by viewModels()
    override fun initView() {
        initObservers()
        viewModel.setupParticipants()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.currentMatch.collect { match ->
                match?.let { showCurrentMatch(it) }
            }
        }

        lifecycleScope.launch {
            viewModel.isRoundComplete.collect { (isComplete, winner) ->
                if (isComplete) navigateToCompletion(winner)
            }
        }
    }

//    private fun initFragmentResultListener() {
//        parentFragmentManager.setFragmentResultListener(
//            RankingFragment.REQUEST_KEY_VOTING_ROOM_ENTER, viewLifecycleOwner
//        ) { _, result ->
//            val confirmedId = result.getString(RankingVotingDialogFragment.RESULT_CONFIRM_KEY)
//            val categoryTitle =
//                result.getString(RankingVotingDialogFragment.ARGUMENT_CATEGORY_TITLE)
//
//            Log.d("VotingFragment", "categoryTitle: $categoryTitle")
//
//            if (confirmedId != null && categoryTitle != null) {
//                binding.tvCategoryTitle.text = categoryTitle
//            }
//        }
//    }

//    private fun setupParticipants() {
//        participants = listOf(
//            TournamentEntity.Participant("1", "참가자1"),
//            TournamentEntity.Participant("2", "참가자2"),
//            TournamentEntity.Participant("3", "참가자3"),
//            TournamentEntity.Participant("4", "참가자4"),
//            TournamentEntity.Participant("5", "참가자5"),
//            TournamentEntity.Participant("6", "참가자6"),
//            TournamentEntity.Participant("7", "참가자7"),
//            TournamentEntity.Participant("8", "참가자8"),
//            TournamentEntity.Participant("9", "참가자9"),
//            TournamentEntity.Participant("10", "참가자10"),
//            TournamentEntity.Participant("11", "참가자11"),
//            TournamentEntity.Participant("12", "참가자12"),
//            TournamentEntity.Participant("13", "참가자13"),
//            TournamentEntity.Participant("14", "참가자14"),
//            TournamentEntity.Participant("15", "참가자15"),
//            TournamentEntity.Participant("16", "참가자16")
//        )
//    }

//    private fun setupMatches() {
//        matches = participants.chunked(2).map { pair ->
//            TournamentEntity.Match(pair[0], pair[1])
//        }
//    }

    private fun showCurrentMatch(match: TournamentEntity.Match) {
//        val currentMatch = matches[currentMatchIndex]
        binding.cvParticipant1.tag = match.participant1
        binding.cvParticipant2.tag = match.participant2

        bind {
            tvParticipant1Name.text = match.participant1.nickName
            tvParticipant2Name.text = match.participant2.nickName
        }

        setupCardViewAnimations()
    }

    private fun navigateToCompletion(winner: TournamentEntity.Participant?) {
        winner?.let {
            val bundle = Bundle().apply {
                putString("winnerName", it.nickName)
                putString("winnerId", it.id)
            }
            findNavController().navigate(R.id.fragmentVotingComplete, bundle)
        }
    }

    private fun setupCardViewAnimations() {
        binding.cvParticipant1.setOnClickListener {
            animateCardViewToCenterThenDisappear(
                selectedCardView = binding.cvParticipant1,
                nonSelectedCardView = binding.cvParticipant2,
                isTopCard = true
            )
            binding.cvParticipant2.isInvisible = true
        }

        binding.cvParticipant2.setOnClickListener {
            animateCardViewToCenterThenDisappear(
                selectedCardView = binding.cvParticipant2,
                nonSelectedCardView = binding.cvParticipant1,
                isTopCard = false
            )
            binding.cvParticipant1.isInvisible = true
        }
    }

//    private fun selectWinner(winner: TournamentEntity.Participant) {
//        matches[currentMatchIndex].winner = winner
//
//        if (currentMatchIndex < matches.size - 1) {
//            currentMatchIndex++
//            showCurrentMatch()
//        } else {
//            setupNextRound()
//        }
//    }

//    private fun setupNextRound() {
//        currentRound /= 2
//        currentMatchIndex = 0
//
//        val winners = matches.mapNotNull { it.winner }
//
//        // 최종 승자가 결정된 경우 (winners 리스트에 한 명만 남은 경우)
//        if (winners.size == 1) {
//            val winner = winners.first()
//            val bundle = Bundle().apply {
//                putString("winnerName", winner.nickName)
//                putString("winnerId", winner.id)
//            }
//            Log.d("VotingFragment", "winnerName: ${winner?.nickName}, winnerId: ${winner?.id}")
//            findNavController().navigate(R.id.fragmentVotingComplete, bundle)
//        } else {
//            // 다음 라운드의 매치를 구성
//            matches = winners.chunked(2).map { pair -> TournamentEntity.Match(pair[0], pair[1]) }
//
//            // 다음 라운드가 있으면 첫 번째 매치를 보여줌
//            if (matches.isNotEmpty()) {
//                showCurrentMatch()
//            }
//        }
//    }

    private fun animateCardViewToCenterThenDisappear(
        selectedCardView: CardView, nonSelectedCardView: CardView, isTopCard: Boolean
    ) {
        val parent = selectedCardView.parent as ViewGroup
        val parentWidth = parent.width
        val parentHeight = parent.height

        // 카드뷰의 현재 위치와 크기
        val cardViewX = selectedCardView.x
        val cardViewY = selectedCardView.y
        val cardViewWidth = selectedCardView.width
        val cardViewHeight = selectedCardView.height

        // 부모 뷰의 중앙 좌표 계산
        val targetX = (parentWidth / 2f) - (cardViewWidth / 2f)
        val targetY = (parentHeight / 2f) - (cardViewHeight / 2f)

        // 1단계: 중앙으로 이동하면서 확대하는 애니메이션 설정
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.5f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.5f)
        val translationX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, targetX - cardViewX)
        val translationY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, targetY - cardViewY)

        val centerAnimator = ObjectAnimator.ofPropertyValuesHolder(
            selectedCardView, scaleX, scaleY, translationX, translationY
        ).apply {
            duration = 300 // 중앙으로 이동하면서 확대하는 애니메이션 지속 시간
        }

        // 멈추는 애니메이션 (0.5초 동안 유지)
        val pauseAnimator = ObjectAnimator.ofFloat(selectedCardView, View.ALPHA, 1f).apply {
            duration = 300 // 중앙에 멈춰있는 시간 (0.5초)
        }

        // 선택된 카드의 애니메이션 설정
        val selectedRotationAngle = 90f
        val selectedTranslationX = parentWidth * 1.5f // 오른쪽으로 이동

        val selectedRotateAnimator =
            ObjectAnimator.ofFloat(selectedCardView, View.ROTATION, 0f, selectedRotationAngle)
        val selectedTranslateAnimator =
            ObjectAnimator.ofFloat(selectedCardView, View.TRANSLATION_X, 0f, selectedTranslationX)

        val selectedAnimatorSet = AnimatorSet().apply {
            playTogether(selectedRotateAnimator, selectedTranslateAnimator)
            duration = 500
        }

        // 선택되지 않은 카드의 애니메이션 설정
        val nonSelectedRotationAngle = -90f
        val nonSelectedTranslationX = -parentWidth * 1.5f // 왼쪽으로 이동

        val nonSelectedRotateAnimator =
            ObjectAnimator.ofFloat(nonSelectedCardView, View.ROTATION, 0f, nonSelectedRotationAngle)
        val nonSelectedTranslateAnimator = ObjectAnimator.ofFloat(
            nonSelectedCardView, View.TRANSLATION_X, 0f, nonSelectedTranslationX
        )

        val nonSelectedAnimatorSet = AnimatorSet().apply {
            playTogether(nonSelectedRotateAnimator, nonSelectedTranslateAnimator)
            duration = 500
        }

        AnimatorSet().apply {
            playSequentially(
                centerAnimator,
                pauseAnimator,
                selectedAnimatorSet,
                nonSelectedAnimatorSet
            )
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                }

                override fun onAnimationEnd(animation: Animator) {
                    // 애니메이션이 끝난 후 카드 초기화 및 다음 참가자 표시
                    resetCardView(selectedCardView)
                    resetCardView(nonSelectedCardView)

                    val participant = selectedCardView.tag as? TournamentEntity.Participant
                    if (participant != null) {
                        viewModel.selectWinner(participant)
                    } else {
                        Log.e("VotingFragment", "Participant tag is null on selectedCardView")
                    }
                }

                override fun onAnimationCancel(animation: Animator) {
                }

                override fun onAnimationRepeat(animation: Animator) {
                }
            })
            start()
        }
    }

    // 카드뷰를 초기 상태로 되돌리는 메서드
    private fun resetCardView(cardView: CardView) {
        cardView.apply {
            scaleX = 1f
            scaleY = 1f
            rotation = 0f
            translationX = 0f
            translationY = 0f
            isInvisible = false // 다시 보이도록 설정
        }
    }
}