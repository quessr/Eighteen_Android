package com.eighteen.eighteenandroid.presentation.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.eighteen.eighteenandroid.databinding.ItemChatRoomBinding
import com.eighteen.eighteenandroid.domain.model.ChatRoom
import com.eighteen.eighteenandroid.presentation.chat.viewholder.ChatRoomViewHolder

class ChatRoomsAdapter(
    private val setSwipeState: (String, Boolean) -> Unit,
    private val getSwipeState: (String) -> Boolean,
    private val onClickExit: (String) -> Unit,
    private val onClickChatRoom: (String) -> Unit
) : ListAdapter<ChatRoom, ChatRoomViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomViewHolder {
        val binding =
            ItemChatRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatRoomViewHolder(
            binding = binding,
            setSwipeState = setSwipeState,
            getSwipeState = getSwipeState,
            onClickExit = onClickExit,
            onClickChatRoom = onClickChatRoom
        ).apply {
            onCreateViewHolder()
        }
    }

    override fun onBindViewHolder(holder: ChatRoomViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<ChatRoom>() {
            override fun areItemsTheSame(oldItem: ChatRoom, newItem: ChatRoom): Boolean =
                oldItem.chatRoomId == newItem.chatRoomId

            override fun areContentsTheSame(oldItem: ChatRoom, newItem: ChatRoom): Boolean =
                oldItem == newItem

        }
    }
}