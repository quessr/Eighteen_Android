package com.eighteen.eighteenandroid.presentation.profiledetail.viewholder

import android.util.Log
import android.view.View
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.ItemProfileDetailBadgeAndTeenBinding
import com.eighteen.eighteenandroid.databinding.ItemProfileDetailImagesWithLikeBinding
import com.eighteen.eighteenandroid.databinding.ItemProfileDetailInfoBinding
import com.eighteen.eighteenandroid.databinding.ItemProfileDetailIntroductionBinding
import com.eighteen.eighteenandroid.databinding.ItemQnaBinding
import com.eighteen.eighteenandroid.databinding.ItemQnaTitleBinding
import com.eighteen.eighteenandroid.databinding.ItemSeeMoreBinding
import com.eighteen.eighteenandroid.presentation.common.media3.viewpager2.ViewPagerPlayerManager
import com.eighteen.eighteenandroid.presentation.profiledetail.ViewPagerAdapter
import com.eighteen.eighteenandroid.presentation.profiledetail.model.ProfileDetailModel
import com.google.android.material.tabs.TabLayoutMediator

sealed class ProfileDetailViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    open fun onBind(profileDetailModel: ProfileDetailModel) {}

    class Info(private val binding: ItemProfileDetailInfoBinding) :
        ProfileDetailViewHolder(binding) {
        override fun onBind(profileDetailModel: ProfileDetailModel) {
            val profileInfo = profileDetailModel as? ProfileDetailModel.ProfileInfo
            profileInfo?.let {
                binding.tvName.text = it.name
                binding.tvAge.text = "${it.age}세"
                binding.tvSchool.text = "${it.school}, "
            }
        }
    }

    class Images(
        val binding: ItemProfileDetailImagesWithLikeBinding,
        private val lifecycleOwner: LifecycleOwner,
        private val onPageChangeCallbackForImagePosition: (Int) -> Unit,
        private val onPageCallbackForVisibilitySoundIcon: ViewPager2.OnPageChangeCallback,
        private val onLikeClickCallback: () -> Unit
    ) : ProfileDetailViewHolder(binding) {
        private val viewPagerPlayerManager: ViewPagerPlayerManager = ViewPagerPlayerManager(
            viewPager2 = binding.viewPager,
            lifecycleOwner = lifecycleOwner,
            context = binding.root.context
        )

        init {
            binding.viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    Log.d("ProfileDetailViewHolder", "onPageSelected")
                    // 페이지 변경 시 콜백을 통해 currentPosition 값을 업데이트 (스크롤시 ViewPager의 위치 유지)
                    onPageChangeCallbackForImagePosition(position)
                }
            })

            binding.viewPager.registerOnPageChangeCallback(onPageCallbackForVisibilitySoundIcon)
            binding.viewPager.offscreenPageLimit = 5
        }

        override fun onBind(profileDetailModel: ProfileDetailModel) {
            val profileImages = profileDetailModel as? ProfileDetailModel.ProfileImages

            profileImages?.let {
                val adapter = ViewPagerAdapter(it.mediaItems)
                binding.viewPager.adapter = adapter
                TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> }.attach()

                binding.ivLike.setImageResource(
                    if (it.isLiked) R.drawable.ic_full_heart else R.drawable.ic_empty_heart
                )
                binding.tvLike.text = profileImages.likeCount.toString()

                binding.ivLike.setOnClickListener {
                    onLikeClickCallback()
                }
            }
        }
    }

    class BadgeAndTeen(private val binding: ItemProfileDetailBadgeAndTeenBinding) :
        ProfileDetailViewHolder(binding) {
        override fun onBind(profileDetailModel: ProfileDetailModel) {

            val badgeAndTeen = profileDetailModel as? ProfileDetailModel.BadgeAndTeen
            badgeAndTeen?.let {
                binding.tvBadgeCount.text = "${it.badgeCount}개"
                binding.tvTeenAward.text = it.teenAward
            }
        }
    }

    class Introduction(private val binding: ItemProfileDetailIntroductionBinding) :
        ProfileDetailViewHolder(binding) {
        override fun onBind(profileDetailModel: ProfileDetailModel) {

            val introduction = profileDetailModel as? ProfileDetailModel.Introduction
            introduction?.let {
                binding.chipPersonalityType.text = it.personalityType
                binding.tvIntroduction.text = it.introductionText
            }
        }
    }

    class QnaTitle(private val binding: ItemQnaTitleBinding) : ProfileDetailViewHolder(binding) {
        override fun onBind(profileDetailModel: ProfileDetailModel) {
            super.onBind(profileDetailModel)
        }
    }

    class Qna(val binding: ItemQnaBinding) : ProfileDetailViewHolder(binding) {
        override fun onBind(profileDetailModel: ProfileDetailModel) {
            val qna = profileDetailModel as? ProfileDetailModel.Qna
            binding.root.post {
                if (qna?.question.isNullOrEmpty()) {
                    binding.question.isVisible = false
                    binding.answer.isVisible = false
                    binding.emptyTextView.isVisible = true
                    binding.root.background = null
                    binding.root.setBackgroundColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.background_color
                        )
                    )
                    binding.root.setPadding(0, 0, 0, 0)
                } else {
                    // qna가 null이 아닌 경우 처리
                    binding.question.text = qna?.question
                    binding.answer.text = qna?.answer
                }
            }
//            qna?.let {
//                binding.question.text = it.question
//                binding.answer.text = it.answer
//            }
        }
    }

    class QnaToggle(
        val binding: ItemSeeMoreBinding,
//        private val toggleItems: (ProfileDetailModel.QnaToggle) -> Unit
        private val onQnaToggleCallback: () -> Unit
    ) : ProfileDetailViewHolder(binding) {
        override fun onBind(profileDetailModel: ProfileDetailModel) {
            val toggle = profileDetailModel as? ProfileDetailModel.QnaToggle
            toggle?.let {
                binding.tvSeeMore.text = if (toggle.isExpanded) "접기" else "펼쳐서 보기"
                binding.tvSeeMore.setOnClickListener(View.OnClickListener {
//                    toggleItems(toggle)
                    onQnaToggleCallback()
                })
                binding.ivSeeMore.setOnClickListener(View.OnClickListener {
//                    toggleItems(toggle)
                    onQnaToggleCallback()
                })
            }
        }
    }
}

