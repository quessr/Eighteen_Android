package com.eighteen.eighteenandroid.presentation.chat.chatroom.viewholder

import com.eighteen.eighteenandroid.databinding.ItemChatMessageReceiveBinding
import com.eighteen.eighteenandroid.presentation.chat.chatroom.model.ChatRoomMessageModel
import com.eighteen.eighteenandroid.presentation.common.imageloader.ImageLoader

class ChatMessageReceiveViewHolder(private val binding: ItemChatMessageReceiveBinding) :
    BaseChatMessagesViewHolder<ChatRoomMessageModel.Message.Receive>(binding) {

    override fun onBind(model: ChatRoomMessageModel.Message.Receive) {
        with(binding) {
            //TODO placeholder
            ImageLoader.get().loadUrl(ivProfileImage, model.imageUrl)
            tvMessage.text = model.message
            tvMessageTime.text = model.messageTimeString
        }
    }
}