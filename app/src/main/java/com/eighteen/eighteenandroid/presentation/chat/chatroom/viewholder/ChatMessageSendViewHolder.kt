package com.eighteen.eighteenandroid.presentation.chat.chatroom.viewholder

import com.eighteen.eighteenandroid.databinding.ItemChatMessageSendBinding
import com.eighteen.eighteenandroid.presentation.chat.chatroom.model.ChatRoomMessageModel

class ChatMessageSendViewHolder(private val binding: ItemChatMessageSendBinding) :
    BaseChatMessagesViewHolder<ChatRoomMessageModel.Message.Send>(binding) {

    override fun onBind(model: ChatRoomMessageModel.Message.Send) {
        with(binding) {
            tvMessage.text = model.message
            tvMessageTime.text = model.messageTimeString
        }
    }
}