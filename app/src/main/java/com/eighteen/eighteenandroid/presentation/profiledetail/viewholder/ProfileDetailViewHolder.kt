package com.eighteen.eighteenandroid.presentation.profiledetail.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.eighteen.eighteenandroid.databinding.ItemProfileDetailImagesBinding
import com.eighteen.eighteenandroid.databinding.ItemProfileDetailInfoBinding
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
}
