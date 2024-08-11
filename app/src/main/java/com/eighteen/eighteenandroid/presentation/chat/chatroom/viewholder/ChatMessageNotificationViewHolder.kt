package com.eighteen.eighteenandroid.presentation.chat.chatroom.viewholder

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.eighteen.eighteenandroid.databinding.ItemChatMessageNotificationBinding
import com.eighteen.eighteenandroid.presentation.chat.chatroom.model.ChatRoomMessageModel

class ChatMessageNotificationViewHolder(private val binding: ItemChatMessageNotificationBinding) :
    ViewHolder(binding.root) {

    fun onBind(model: ChatRoomMessageModel.Notification) {
        with(binding) {
            ivIcon.isVisible = model.iconDrawableRes != null
            model.iconDrawableRes?.let {
                ivIcon.setImageResource(it)
            }
            tvMessage.text = model.message
        }
    }
}