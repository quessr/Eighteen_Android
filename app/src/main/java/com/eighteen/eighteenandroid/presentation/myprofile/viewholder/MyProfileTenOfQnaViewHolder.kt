package com.eighteen.eighteenandroid.presentation.myprofile.viewholder

import com.eighteen.eighteenandroid.databinding.ItemMyProfileTenOfQnaBinding
import com.eighteen.eighteenandroid.presentation.myprofile.MyProfileClickListener
import com.eighteen.eighteenandroid.presentation.myprofile.model.MyProfileItem

class MyProfileTenOfQnaViewHolder(
    private val clickListener: MyProfileClickListener,
    private val binding: ItemMyProfileTenOfQnaBinding
) : BaseMyProfileViewHolder<MyProfileItem.TenOfQna, ItemMyProfileTenOfQnaBinding>(binding = binding) {
}