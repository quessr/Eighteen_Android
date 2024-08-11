package com.eighteen.eighteenandroid.presentation.chat.chatroom.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.eighteen.eighteenandroid.databinding.ItemChatMessageTimeBinding
import com.eighteen.eighteenandroid.presentation.chat.chatroom.model.ChatRoomMessageModel

class ChatMessageTimeViewHolder(private val binding: ItemChatMessageTimeBinding) :
    ViewHolder(binding.root) {

    fun onBind(model: ChatRoomMessageModel.Time) {
        binding.tvTime.text = model.timeString
    }
}