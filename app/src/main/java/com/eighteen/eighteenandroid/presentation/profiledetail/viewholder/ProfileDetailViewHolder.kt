package com.eighteen.eighteenandroid.presentation.profiledetail.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.eighteen.eighteenandroid.databinding.ItemProfileDetailBadgeAndTeenBinding
import com.eighteen.eighteenandroid.databinding.ItemProfileDetailImagesWithLikeBinding
import com.eighteen.eighteenandroid.databinding.ItemProfileDetailInfoBinding
import com.eighteen.eighteenandroid.databinding.ItemProfileDetailIntroductionBinding
import com.eighteen.eighteenandroid.databinding.ItemQnaBinding
import com.eighteen.eighteenandroid.databinding.ItemQnaTitleBinding
import com.eighteen.eighteenandroid.databinding.ItemSeeMoreBinding
import com.eighteen.eighteenandroid.presentation.profiledetail.ProfileDetailViewModel
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

    class Images(private val binding: ItemProfileDetailImagesWithLikeBinding) :
        ProfileDetailViewHolder(binding) {
        override fun onBind(profileDetailModel: ProfileDetailModel) {
            val profileImages = profileDetailModel as? ProfileDetailModel.ProfileImages
            profileImages?.let {
                val adapter = ViewPagerAdapter(it.imageUrl)
                binding.viewPager.adapter = adapter
                TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> }.attach()
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

//    class Qna(
//        private val binding: ItemProfileDetailQnaBinding,
//        lifecycleOwner: LifecycleOwner,
//        viewModel: ProfileDetailViewModel
//    ) : ProfileDetailViewHolder(binding) {
//        private val qnaAdapter = QuestionAnswerAdapter(lifecycleOwner, viewModel)
//
//        init {
//            binding.recyclerView.isNestedScrollingEnabled = false
//            binding.recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
//            binding.recyclerView.adapter = qnaAdapter
//        }
//
//        override fun onBind(profileDetailModel: ProfileDetailModel) {
//            val qnaList = profileDetailModel as? ProfileDetailModel.QnaList
//            qnaList?.let {
//                Log.d("Qna", "Qna list size: ${it.qnas.size}")
//                qnaAdapter.submitList(it.qnas)
//            }
//        }
//    }

    class QnaTitle(private val binding: ItemQnaTitleBinding) : ProfileDetailViewHolder(binding) {
        override fun onBind(profileDetailModel: ProfileDetailModel) {
            super.onBind(profileDetailModel)
        }
    }

    class Qna(val binding: ItemQnaBinding) : ProfileDetailViewHolder(binding) {
        override fun onBind(profileDetailModel: ProfileDetailModel) {
            val qna = profileDetailModel as? ProfileDetailModel.Qna
            qna.let {
                binding.question.text = it?.question
                binding.answer.text = it?.question
            }
        }
    }

    class QnaToggle(
        val binding: ItemSeeMoreBinding,
        private val profileDetailViewModel: ProfileDetailViewModel
    ) :
        ProfileDetailViewHolder(binding) {
        override fun onBind(profileDetailModel: ProfileDetailModel) {
            val toggle = profileDetailModel as? ProfileDetailModel.QnaToggle
            toggle?.let {
                binding.tvSeeMore.text = if (toggle.isExpanded) "접기" else "펼쳐서 보기"
                binding.tvSeeMore.setOnClickListener(View.OnClickListener {
                    toggleItems(toggle)
                    profileDetailViewModel.toggleItems()
                })
                binding.ivSeeMore.setOnClickListener(View.OnClickListener {
                    profileDetailViewModel.toggleItems()
                    toggleItems(toggle)
                })
            }
        }

        private fun toggleItems(toggle: ProfileDetailModel.QnaToggle) {
            val showItems = toggle.isExpanded
            val updateToggle = toggle.copy(isExpanded = !showItems)
            profileDetailViewModel.updateQnaToggle(updateToggle)
        }
    }

}

