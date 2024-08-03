package com.eighteen.eighteenandroid.presentation.mediadetail.viewholder

import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.eighteen.eighteenandroid.databinding.ItemMediaDetailImageBinding
import com.eighteen.eighteenandroid.databinding.ItemMediaDetailVideoBinding
import com.eighteen.eighteenandroid.presentation.common.imageloader.ImageLoader
import com.eighteen.eighteenandroid.presentation.common.media3.MediaInfo
import com.eighteen.eighteenandroid.presentation.common.media3.viewpager2.ViewPagerMediaItem
import com.eighteen.eighteenandroid.presentation.mediadetail.model.MediaDetailMediaModel

//TODO 이미지 placeholder 추가
sealed class MediaDetailViewHolder(
    binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    open fun onBind(mediaDetailMediaModel: MediaDetailMediaModel) {}

    class Video(private val binding: ItemMediaDetailVideoBinding) :
        MediaDetailViewHolder(binding), ViewPagerMediaItem {

        private var videoModel: MediaDetailMediaModel.Video? = null
        override fun onBind(mediaDetailMediaModel: MediaDetailMediaModel) {
            videoModel = mediaDetailMediaModel as? MediaDetailMediaModel.Video
            binding.mvMedia.setThumbnailUrl(url = mediaDetailMediaModel.mediaUrl)
        }

        @OptIn(UnstableApi::class)
        override fun getMediaInfo() = MediaInfo(
            id = videoModel?.id,
            mediaUrl = videoModel?.mediaUrl ?: "",
            mediaView = binding.mvMedia
        )
    }

    class Image(private val binding: ItemMediaDetailImageBinding) :
        MediaDetailViewHolder(binding) {

        override fun onBind(mediaDetailMediaModel: MediaDetailMediaModel) {
            ImageLoader.get()
                .loadUrl(imageView = binding.ivImage, url = mediaDetailMediaModel.mediaUrl)
        }
    }
}