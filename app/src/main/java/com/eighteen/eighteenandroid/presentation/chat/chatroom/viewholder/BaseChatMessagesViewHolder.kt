package com.eighteen.eighteenandroid.presentation.chat.chatroom.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import com.eighteen.eighteenandroid.presentation.chat.chatroom.model.ChatRoomMessageModel

abstract class BaseChatMessagesViewHolder<T : ChatRoomMessageModel>(binding: ViewBinding) :
    ViewHolder(binding.root) {
    open fun onBind(model: T) {}
}