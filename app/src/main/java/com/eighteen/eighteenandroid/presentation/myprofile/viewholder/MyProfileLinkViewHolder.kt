package com.eighteen.eighteenandroid.presentation.myprofile.viewholder

import com.eighteen.eighteenandroid.databinding.ItemMyProfileLinkBinding
import com.eighteen.eighteenandroid.presentation.myprofile.MyProfileClickListener
import com.eighteen.eighteenandroid.presentation.myprofile.model.MyProfileItem

class MyProfileLinkViewHolder(
    private val clickListener: MyProfileClickListener,
    private val binding: ItemMyProfileLinkBinding
) : BaseMyProfileViewHolder<MyProfileItem.Link, ItemMyProfileLinkBinding>(
    binding = binding
) {

}