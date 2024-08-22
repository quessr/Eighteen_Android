package com.eighteen.eighteenandroid.presentation.chat.chatroom.viewholder

import com.eighteen.eighteenandroid.databinding.ItemChatMessageTimeBinding
import com.eighteen.eighteenandroid.presentation.chat.chatroom.model.ChatRoomMessageModel

class ChatMessageTimeViewHolder(private val binding: ItemChatMessageTimeBinding) :
    BaseChatMessagesViewHolder<ChatRoomMessageModel.Time>(binding) {

    override fun onBind(model: ChatRoomMessageModel.Time) {
        binding.tvTime.text = model.timeString
    }
}