package com.eighteen.eighteenandroid.presentation.ranking.voting

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import androidx.cardview.widget.CardView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.eighteen.eighteenandroid.databinding.FragmentVotingBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.eighteen.eighteenandroid.presentation.ranking.RankingFragment
import com.eighteen.eighteenandroid.presentation.ranking.RankingVotingDialogFragment

class VotingFragment : BaseFragment<FragmentVotingBinding>(FragmentVotingBinding::inflate) {
    override fun initView() {
        initFragmentResultListener()
        setupCardViewAnimations()
    }

    private fun initFragmentResultListener() {
        parentFragmentManager.setFragmentResultListener(
            RankingFragment.REQUEST_KEY_VOTING_ROOM_ENTER,
            viewLifecycleOwner
        ) { _, result ->
            val confirmedId = result.getString(RankingVotingDialogFragment.RESULT_CONFIRM_KEY)
            val categoryTitle =
                result.getString(RankingVotingDialogFragment.ARGUMENT_CATEGORY_TITLE)

            Log.d("VotingFragment", "categoryTitle: $categoryTitle")

            if (confirmedId != null && categoryTitle != null) {
                binding.tvCategoryTitle.text = categoryTitle
            }
        }
    }

    private fun setupCardViewAnimations() {
        binding.cvParticipant1.setOnClickListener {
            animateCardViewToCenterThenDisappear(binding.cvParticipant1, isTopCard = true)
            binding.cvParticipant2.isInvisible = true
        }

        binding.cvParticipant2.setOnClickListener {
            animateCardViewToCenterThenDisappear(binding.cvParticipant2, isTopCard = false)
            binding.cvParticipant1.isInvisible = true
        }
    }

    private fun animateCardViewToCenterThenDisappear(cardView: CardView, isTopCard: Boolean) {
        val parent = cardView.parent as ViewGroup
        val parentWidth = parent.width
        val parentHeight = parent.height

        // 카드뷰의 현재 위치와 크기
        val cardViewX = cardView.x
        val cardViewY = cardView.y
        val cardViewWidth = cardView.width
        val cardViewHeight = cardView.height

        // 부모 뷰의 중앙 좌표 계산
        val targetX = (parentWidth / 2f) - (cardViewWidth / 2f)
        val targetY = (parentHeight / 2f) - (cardViewHeight / 2f)

        // 중앙으로 확대하는 애니메이션 설정
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.5f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.5f)
        val translationX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, targetX - cardViewX)
        val translationY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, targetY - cardViewY)

        val centerAnimator = ObjectAnimator.ofPropertyValuesHolder(
            cardView, scaleX, scaleY, translationX, translationY
        ).apply {
            duration = 300 // 중앙으로 확대하는 애니메이션 지속 시간
        }

        // 회전 및 화면 밖으로 사라지는 애니메이션 설정
        // TODO 선택된 경우 오른쪽으로, 선택 안된 경우 왼쪽으로 애니메이션 수정
        val rotationAngle = if (isTopCard) -90f else 90f
        val disappearTranslationX = if (isTopCard) -parent.width.toFloat() else parent.width.toFloat()

        val rotateAnimator = ObjectAnimator.ofFloat(cardView, View.ROTATION, 0f, rotationAngle)
        val translateAnimator = ObjectAnimator.ofFloat(cardView, View.TRANSLATION_X, targetX - cardViewX, disappearTranslationX)

        val disappearAnimatorSet = AnimatorSet().apply {
            playTogether(rotateAnimator, translateAnimator)
            duration = 500 // 회전하며 사라지는 애니메이션 지속 시간
        }

        AnimatorSet().apply {
            playSequentially(centerAnimator, disappearAnimatorSet)
            start() // 애니메이션 시작
        }
    }
}