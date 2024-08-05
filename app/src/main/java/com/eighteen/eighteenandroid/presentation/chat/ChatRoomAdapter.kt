package com.eighteen.eighteenandroid.presentation.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.eighteen.eighteenandroid.databinding.ItemChatRoomExitBinding
import com.eighteen.eighteenandroid.databinding.ItemChatRoomInfoBinding
import com.eighteen.eighteenandroid.domain.model.ChatRoom
import com.eighteen.eighteenandroid.presentation.chat.viewholder.ChatRoomExitViewHolder
import com.eighteen.eighteenandroid.presentation.chat.viewholder.ChatRoomInfoViewHolder

class ChatRoomAdapter(private val onClickExit: (String) -> Unit) :
    Adapter<RecyclerView.ViewHolder>() {
    private var chatRoom: ChatRoom? = null
    fun setData(chatRoom: ChatRoom) {
        this.chatRoom = chatRoom
        notifyItemRangeChanged(0, 2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == ITEM_TYPE_INFO) {
            val binding = ItemChatRoomInfoBinding.inflate(layoutInflater, parent, false)
            ChatRoomInfoViewHolder(binding = binding)
        } else {
            val binding = ItemChatRoomExitBinding.inflate(layoutInflater, parent, false)
            ChatRoomExitViewHolder(binding = binding, onClickExit = onClickExit)
        }
    }

    override fun getItemCount(): Int = if (chatRoom == null) 0 else 2

    override fun getItemViewType(position: Int): Int = position

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        chatRoom?.let {
            if (holder is ChatRoomInfoViewHolder)
                holder.onBind(it)
            else if (holder is ChatRoomExitViewHolder) {
                holder.onBind(it.chatRoomId)
            }
        }
    }

    companion object {
        private const val ITEM_TYPE_INFO = 0
        private const val ITEM_TYPE_EXIT = 1
    }
}