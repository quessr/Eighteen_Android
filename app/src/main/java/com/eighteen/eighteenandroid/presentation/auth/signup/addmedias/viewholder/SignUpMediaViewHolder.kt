package com.eighteen.eighteenandroid.presentation.auth.signup.addmedias.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.ItemSignUpMediaBinding
import com.eighteen.eighteenandroid.presentation.auth.signup.addmedias.SignUpAddMediasClickListener
import com.eighteen.eighteenandroid.presentation.auth.signup.addmedias.model.SignUpMedia
import com.eighteen.eighteenandroid.presentation.common.imageloader.ImageLoader

//TODO 디자인 리소스 적용
class SignUpMediaViewHolder(
    private val binding: ItemSignUpMediaBinding,
    private val clickListener: SignUpAddMediasClickListener
) : ViewHolder(binding.root) {

    fun onBind(model: SignUpMedia, position: Int) {
        bindImage(model = model)
        bindClickListener(model = model, position = position)
    }

    private fun bindImage(model: SignUpMedia) {
        when (model) {
            is SignUpMedia.Empty -> {
                binding.ivMedia.run {
                    setImageResource(R.drawable.ic_launcher_background)
                }
            }
            is SignUpMedia.Image -> {
                ImageLoader.get().loadUrl(binding.ivMedia, model.imageUrl)
                binding.ivMedia.setOnClickListener {

                }
            }
            is SignUpMedia.Video -> {
                ImageLoader.get().loadUrl(binding.ivMedia, model.thumbnailUrl)
            }
        }
    }

    private fun bindClickListener(model: SignUpMedia, position: Int) {
        binding.ivMedia.setOnClickListener {
            when (model) {
                is SignUpMedia.Empty -> clickListener.onClickAddMedia(position)
                is SignUpMedia.Image -> clickListener.onClickMedia()
                is SignUpMedia.Video -> clickListener.onClickMedia()
            }
        }
    }
}