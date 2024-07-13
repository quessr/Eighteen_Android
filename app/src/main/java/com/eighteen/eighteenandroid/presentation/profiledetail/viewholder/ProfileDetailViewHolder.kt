package com.eighteen.eighteenandroid.presentation.profiledetail.viewholder

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.eighteen.eighteenandroid.databinding.ItemProfileDetailBadgeAndTeenBinding
import com.eighteen.eighteenandroid.databinding.ItemProfileDetailImagesBinding
import com.eighteen.eighteenandroid.databinding.ItemProfileDetailInfoBinding
import com.eighteen.eighteenandroid.databinding.ItemProfileDetailIntroductionBinding
import com.eighteen.eighteenandroid.databinding.ItemProfileDetailQnaBinding
import com.eighteen.eighteenandroid.presentation.profiledetail.ProfileDetailViewModel
import com.eighteen.eighteenandroid.presentation.profiledetail.QuestionAnswerAdapter
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

    class Images(private val binding: ItemProfileDetailImagesBinding) :
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

    class Qna(
        private val binding: ItemProfileDetailQnaBinding,
        lifecycleOwner: LifecycleOwner,
        viewModel: ProfileDetailViewModel
    ) : ProfileDetailViewHolder(binding) {
        private val qnaAdapter = QuestionAnswerAdapter(lifecycleOwner, viewModel)

        init {
            binding.recyclerView.isNestedScrollingEnabled = false
            binding.recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
            binding.recyclerView.adapter = qnaAdapter
        }

        override fun onBind(profileDetailModel: ProfileDetailModel) {
            val qnaList = profileDetailModel as? ProfileDetailModel.QnaList
            qnaList?.let {
                Log.d("Qna", "Qna list size: ${it.qnas.size}")
                qnaAdapter.submitList(it.qnas)
            }
        }
    }
}
