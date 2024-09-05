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
        binding.vRefContainer.isVisible = model.isRef
        when (model) {
            is SignUpMediaItemModel.Empty -> {
                val drawableRes = if (model.isRef) R.drawable.ic_add_media_ref else
                    R.drawable.bg_add_media_empty
                binding.ivMedia.setImageResource(drawableRes)
            }
            is SignUpMediaItemModel.Image -> ImageLoader.get()
                .loadBitmapCenterCrop(binding.ivMedia, model.imageBitmap)
            is SignUpMediaItemModel.Video -> ImageLoader.get()
                .loadUrlCenterCrop(binding.ivMedia, url = model.uriString)
        }
        binding.ivBtnDelete.isVisible = model !is SignUpMediaItemModel.Empty
        binding.ivVideoIcon.isVisible = model is SignUpMediaItemModel.Video
    }

    private fun bindClickListener(model: SignUpMediaItemModel, position: Int) {
        binding.ivMedia.setOnClickListener {
            if (model is SignUpMediaItemModel.Empty) {
                if (model.isRef) clickListener.onClickAddRefMedia()
                else clickListener.onClickAddMedia()
            } else {
                //do nothing
            }
        }
        binding.ivBtnDelete.setOnClickListener {
            //대표이미지를 제외하고 위치(index)를 넣어주어야 함
            if (model.isRef) clickListener.onClickRemoveRef()
            else clickListener.onClickRemove(position = position - 1)

        }
    }
}