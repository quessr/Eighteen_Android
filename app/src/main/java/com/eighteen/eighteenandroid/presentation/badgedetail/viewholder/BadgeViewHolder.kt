package com.eighteen.eighteenandroid.presentation.badgedetail.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.ItemBadgeDetailBadgeBinding
import com.eighteen.eighteenandroid.presentation.badgedetail.model.BadgeDetailModel
import com.eighteen.eighteenandroid.presentation.common.imageloader.ImageLoader

class BadgeViewHolder(
    private val binding: ItemBadgeDetailBadgeBinding,
    private val onClick: (BadgeDetailModel) -> Unit
) : ViewHolder(binding.root) {
    fun onBind(model: BadgeDetailModel) {
        with(binding) {
            ImageLoader.get()
                .loadUrlCircleCrop(ivBadgeImage, model.imageUrl, R.drawable.bg_oval_divider)
            tvBadgeName.text = model.badgeName
            root.setOnClickListener {
                onClick(model)
            }
        }
    }
}