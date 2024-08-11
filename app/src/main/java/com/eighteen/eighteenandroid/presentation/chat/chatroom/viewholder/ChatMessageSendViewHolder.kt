package com.eighteen.eighteenandroid.presentation.chat.chatroom.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.eighteen.eighteenandroid.databinding.ItemChatMessageSendBinding
import com.eighteen.eighteenandroid.presentation.chat.chatroom.model.ChatRoomMessageModel

class ChatMessageSendViewHolder(private val binding: ItemChatMessageSendBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(model: ChatRoomMessageModel.Message.Send) {
        with(binding) {
            tvMessage.text = model.message
            tvMessageTime.text = model.messageTimeString
        }
    }
}