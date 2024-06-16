package com.eighteen.eighteenandroid.presentation.auth.signup.addmedias

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.eighteen.eighteenandroid.databinding.ItemSignUpMediaBinding
import com.eighteen.eighteenandroid.presentation.auth.signup.addmedias.model.SignUpMedia
import com.eighteen.eighteenandroid.presentation.auth.signup.addmedias.viewholder.SignUpMediaViewHolder

class SignUpMediasAdapter(private val clickListener: SignUpAddMediasClickListener) :
    ListAdapter<SignUpMedia, SignUpMediaViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SignUpMediaViewHolder {
        val binding =
            ItemSignUpMediaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SignUpMediaViewHolder(binding = binding, clickListener = clickListener)
    }

    override fun onBindViewHolder(holder: SignUpMediaViewHolder, position: Int) {
        holder.onBind(getItem(position), position)
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<SignUpMedia>() {
            override fun areItemsTheSame(oldItem: SignUpMedia, newItem: SignUpMedia): Boolean = true

            override fun areContentsTheSame(oldItem: SignUpMedia, newItem: SignUpMedia): Boolean =
                (oldItem is SignUpMedia.Empty && newItem is SignUpMedia.Empty) ||
                        (oldItem is SignUpMedia.Video && newItem is SignUpMedia.Video && oldItem.thumbnailUrl == newItem.thumbnailUrl) ||
                        (oldItem is SignUpMedia.Image && newItem is SignUpMedia.Image && oldItem.imageUrl == newItem.imageUrl)
        }
    }
}