package com.eighteen.eighteenandroid.presentation.myprofile.editlink

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.eighteen.eighteenandroid.databinding.ItemLinkBinding
import com.eighteen.eighteenandroid.domain.model.SnsLink
import com.eighteen.eighteenandroid.presentation.myprofile.editlink.viewholder.EditLinkViewHolder

class EditLinkDialogAdapter(
    private val onClickRemove: (Int) -> Unit,
    private val findAdapterPosition: (View) -> Int
) : ListAdapter<SnsLink, EditLinkViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditLinkViewHolder {
        val binding =
            ItemLinkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EditLinkViewHolder(
            binding = binding,
            onClickRemove = onClickRemove,
            findAdapterPosition = findAdapterPosition
        )
    }

    override fun onBindViewHolder(holder: EditLinkViewHolder, position: Int) {
        holder.onBind(data = getItem(position))
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<SnsLink>() {
            override fun areItemsTheSame(oldItem: SnsLink, newItem: SnsLink) =
                oldItem.linkUrl == newItem.linkUrl

            override fun areContentsTheSame(oldItem: SnsLink, newItem: SnsLink) =
                oldItem == newItem
        }
    }
}