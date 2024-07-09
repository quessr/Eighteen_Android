package com.eighteen.eighteenandroid.presentation.auth.signup.addmedias.viewholder

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.ItemSignUpMediaBinding
import com.eighteen.eighteenandroid.presentation.auth.signup.addmedias.SignUpAddMediasClickListener
import com.eighteen.eighteenandroid.presentation.auth.signup.addmedias.model.SignUpMediaItemModel
import com.eighteen.eighteenandroid.presentation.common.imageloader.ImageLoader

class SignUpMediaViewHolder(
    private val binding: ItemSignUpMediaBinding,
    private val clickListener: SignUpAddMediasClickListener,
) : ViewHolder(binding.root) {

    fun onBind(model: SignUpMediaItemModel, position: Int) {
        bindImage(model = model)
        bindClickListener(model = model, position = position)
    }

    private fun bindImage(model: SignUpMediaItemModel) {
        when (model) {
            is SignUpMediaItemModel.RefEmpty -> binding.ivMedia.setImageResource(R.drawable.bg_add_media_ref_empty)
            is SignUpMediaItemModel.Empty -> binding.ivMedia.setImageResource(R.drawable.bg_add_media_empty)
            is SignUpMediaItemModel.Image -> ImageLoader.get()
                .loadBitmapCenterCrop(binding.ivMedia, model.imageBitmap)
            is SignUpMediaItemModel.Video -> ImageLoader.get()
                .loadUrlCenterCrop(binding.ivMedia, url = model.uriString)
        }
        binding.ivVideoIcon.isVisible = model is SignUpMediaItemModel.Video
    }

    private fun bindClickListener(model: SignUpMediaItemModel, position: Int) {
        binding.ivMedia.setOnClickListener {
            when (model) {
                is SignUpMediaItemModel.RefEmpty -> clickListener.onClickAddMedia(position)
                is SignUpMediaItemModel.Empty -> clickListener.onClickAddMedia(position)
                is SignUpMediaItemModel.Image -> clickListener.onClickMedia()
                is SignUpMediaItemModel.Video -> clickListener.onClickMedia()
            }
        }
    }
}