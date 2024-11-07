package com.eighteen.eighteenandroid.presentation.badgedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.eighteen.eighteenandroid.databinding.ItemBadgeDetailBadgeBinding
import com.eighteen.eighteenandroid.presentation.badgedetail.model.BadgeDetailModel
import com.eighteen.eighteenandroid.presentation.badgedetail.viewholder.BadgeViewHolder

class BadgeDetailAdapter(private val onClickBadge: (BadgeDetailModel) -> Unit) :
    ListAdapter<BadgeDetailModel, BadgeViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BadgeViewHolder {
        val binding =
            ItemBadgeDetailBadgeBinding.inflate(LayoutInflater.from(parent.context), null, false)
        return BadgeViewHolder(binding = binding, onClick = onClickBadge)
    }

    override fun onBindViewHolder(holder: BadgeViewHolder, position: Int) {
        holder.onBind(model = getItem(position))
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<BadgeDetailModel>() {
            override fun areItemsTheSame(
                oldItem: BadgeDetailModel,
                newItem: BadgeDetailModel
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: BadgeDetailModel,
                newItem: BadgeDetailModel
            ): Boolean = oldItem == newItem
        }
    }
}