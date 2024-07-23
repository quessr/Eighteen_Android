package com.eighteen.eighteenandroid.presentation.home.adapter.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.eighteen.eighteenandroid.domain.model.AboutTeen


class AboutTeenDiffCallBack: DiffUtil.ItemCallback<AboutTeen>() {
    override fun areItemsTheSame(oldItem: AboutTeen, newItem: AboutTeen): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: AboutTeen, newItem: AboutTeen): Boolean {
        return oldItem == newItem
    }
}