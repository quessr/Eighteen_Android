package com.eighteen.eighteenandroid.presentation.mediadetail.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.eighteen.eighteenandroid.databinding.ItemMediaDetailBinding
import com.eighteen.eighteenandroid.presentation.common.media3.MediaInfo
import com.eighteen.eighteenandroid.presentation.common.media3.viewpager2.ViewPagerMediaItem
import com.eighteen.eighteenandroid.presentation.mediadetail.model.MediaDetailModel

class MediaDetailViewHolder(
    private val binding: ItemMediaDetailBinding
) : RecyclerView.ViewHolder(binding.root), ViewPagerMediaItem {

    private var model: MediaDetailModel? = null

    fun bind(mediaDetailModel: MediaDetailModel) {
        model = mediaDetailModel
    }

    override fun getMediaInfo(): MediaInfo {
        val id = model?.id
        val url = model?.let {
            when (it) {
                is MediaDetailModel.Video -> it.videoUrl
                is MediaDetailModel.Image -> it.imageUrl
            }
        }
        return MediaInfo(id, url, binding.mvMedia)
    }
}