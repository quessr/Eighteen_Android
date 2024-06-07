package com.eighteen.eighteenandroid.presentation.mediadetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.eighteen.eighteenandroid.databinding.ItemMediaDetailImageBinding
import com.eighteen.eighteenandroid.databinding.ItemMediaDetailVideoBinding
import com.eighteen.eighteenandroid.presentation.mediadetail.model.MediaDetailModel
import com.eighteen.eighteenandroid.presentation.mediadetail.viewholder.MediaDetailViewHolder

class MediaDetailPagerAdapter : ListAdapter<MediaDetailModel, MediaDetailViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaDetailViewHolder {
        val context = parent.context

        fun <VB : ViewBinding> inflateBinding(bindingInflate: (LayoutInflater, ViewGroup, Boolean) -> VB) =
            bindingInflate.invoke(LayoutInflater.from(context), parent, false)

        inflateBinding(ItemMediaDetailVideoBinding::inflate)
        return when (viewType) {
            ITEM_TYPE_VIDEO -> {
                val binding = inflateBinding(ItemMediaDetailVideoBinding::inflate)
                MediaDetailViewHolder.Video(binding)
            }
            else -> {
                val binding = inflateBinding(ItemMediaDetailImageBinding::inflate)
                MediaDetailViewHolder.Image(binding)
            }
        }
    }

    override fun getItemViewType(position: Int) =
        when (getItem(position)) {
            is MediaDetailModel.Video -> ITEM_TYPE_VIDEO
            is MediaDetailModel.Image -> ITEM_TYPE_IMAGE
        }


    override fun onBindViewHolder(holder: MediaDetailViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)
    }

    companion object {
        private const val ITEM_TYPE_VIDEO = 1
        private const val ITEM_TYPE_IMAGE = 2

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