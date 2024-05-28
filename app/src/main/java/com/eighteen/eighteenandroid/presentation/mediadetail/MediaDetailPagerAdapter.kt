package com.eighteen.eighteenandroid.presentation.mediadetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.eighteen.eighteenandroid.databinding.ItemMediaDetailBinding
import com.eighteen.eighteenandroid.presentation.mediadetail.model.MediaDetailModel
import com.eighteen.eighteenandroid.presentation.mediadetail.viewholder.MediaDetailViewHolder

class MediaDetailPagerAdapter(
    private val mediaDetailModels: List<MediaDetailModel>
) : ListAdapter<MediaDetailModel, MediaDetailViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaDetailViewHolder {
        val binding =
            ItemMediaDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MediaDetailViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: MediaDetailViewHolder, position: Int) {
        mediaDetailModels.getOrNull(position)?.let {
            holder.bind(mediaDetailModel = it)
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<MediaDetailModel>() {
            override fun areItemsTheSame(
                oldItem: MediaDetailModel,
                newItem: MediaDetailModel
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: MediaDetailModel,
                newItem: MediaDetailModel
            ): Boolean = oldItem == newItem
        }
    }
}