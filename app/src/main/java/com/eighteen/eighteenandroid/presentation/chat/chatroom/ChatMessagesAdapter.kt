package com.eighteen.eighteenandroid.presentation.chat.chatroom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.eighteen.eighteenandroid.databinding.ItemChatMessageNotificationBinding
import com.eighteen.eighteenandroid.databinding.ItemChatMessageReceiveBinding
import com.eighteen.eighteenandroid.databinding.ItemChatMessageSendBinding
import com.eighteen.eighteenandroid.databinding.ItemChatMessageTimeBinding
import com.eighteen.eighteenandroid.presentation.chat.chatroom.model.ChatRoomMessageModel
import com.eighteen.eighteenandroid.presentation.chat.chatroom.viewholder.BaseChatMessagesViewHolder
import com.eighteen.eighteenandroid.presentation.chat.chatroom.viewholder.ChatMessageNotificationViewHolder
import com.eighteen.eighteenandroid.presentation.chat.chatroom.viewholder.ChatMessageReceiveViewHolder
import com.eighteen.eighteenandroid.presentation.chat.chatroom.viewholder.ChatMessageSendViewHolder
import com.eighteen.eighteenandroid.presentation.chat.chatroom.viewholder.ChatMessageTimeViewHolder

class ChatMessagesAdapter :
    ListAdapter<ChatRoomMessageModel, BaseChatMessagesViewHolder<out ChatRoomMessageModel>>(diffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseChatMessagesViewHolder<out ChatRoomMessageModel> {
        fun <VB : ViewBinding> inflateBinding(bindingInflate: (LayoutInflater, ViewGroup, Boolean) -> VB) =
            bindingInflate.invoke(LayoutInflater.from(parent.context), parent, false)

        return when (viewType) {
            ITEM_TYPE_SEND -> ChatMessageSendViewHolder(
                binding = inflateBinding(
                    ItemChatMessageSendBinding::inflate
                )
            )
            ITEM_TYPE_RECEIVE -> ChatMessageReceiveViewHolder(
                binding = inflateBinding(
                    ItemChatMessageReceiveBinding::inflate
                )
            )
            ITEM_TYPE_TIME -> ChatMessageTimeViewHolder(
                binding = inflateBinding(
                    ItemChatMessageTimeBinding::inflate
                )
            )
            else -> ChatMessageNotificationViewHolder(
                binding = inflateBinding(
                    ItemChatMessageNotificationBinding::inflate
                )
            )
        }
    }

    override fun onBindViewHolder(
        holder: BaseChatMessagesViewHolder<out ChatRoomMessageModel>,
        position: Int
    ) {
        when (val item = getItem(position)) {
            is ChatRoomMessageModel.Message.Send -> (holder as? ChatMessageSendViewHolder)?.onBind(
                item
            )
            is ChatRoomMessageModel.Message.Receive -> (holder as? ChatMessageReceiveViewHolder)?.onBind(
                item
            )
            is ChatRoomMessageModel.Time -> (holder as? ChatMessageTimeViewHolder)?.onBind(item)
            is ChatRoomMessageModel.Notification -> (holder as? ChatMessageNotificationViewHolder)?.onBind(
                item
            )
        }
    }


    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is ChatRoomMessageModel.Message.Send -> ITEM_TYPE_SEND
        is ChatRoomMessageModel.Message.Receive -> ITEM_TYPE_RECEIVE
        is ChatRoomMessageModel.Time -> ITEM_TYPE_TIME
        else -> ITEM_TYPE_NOTIFICATION
    }

    companion object {
        private const val ITEM_TYPE_SEND = 1
        private const val ITEM_TYPE_RECEIVE = 2
        private const val ITEM_TYPE_TIME = 3
        private const val ITEM_TYPE_NOTIFICATION = 4
        private val diffUtil = object : DiffUtil.ItemCallback<ChatRoomMessageModel>() {
            override fun areItemsTheSame(
                oldItem: ChatRoomMessageModel,
                newItem: ChatRoomMessageModel
            ) = oldItem.areItemsTheSame(newItem)

            override fun areContentsTheSame(
                oldItem: ChatRoomMessageModel,
                newItem: ChatRoomMessageModel
            ) = oldItem.areContentsTheSame(newItem)
        }
    }

}