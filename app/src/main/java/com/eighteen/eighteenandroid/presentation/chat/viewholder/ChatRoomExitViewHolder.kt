package com.eighteen.eighteenandroid.presentation.chat.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.eighteen.eighteenandroid.databinding.ItemChatRoomExitBinding

class ChatRoomExitViewHolder(
    private val binding: ItemChatRoomExitBinding,
    private val onClickExit: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(chatRoomId: String) {
        binding.root.setOnClickListener {
            onClickExit.invoke(chatRoomId)
        }
    }
}