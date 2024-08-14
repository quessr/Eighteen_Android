package com.eighteen.eighteenandroid.presentation.chat.chatroom

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.eighteen.eighteenandroid.presentation.chat.chatroom.model.ChatRoomMessageModel
import com.eighteen.eighteenandroid.presentation.common.dp2Px

class ChatMessageItemDecoration : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val adapterPosition = parent.getChildAdapterPosition(view)
        val adapter = (parent.adapter as? ChatMessagesAdapter) ?: return
        val currentList = adapter.currentList
        val currentItem = currentList.getOrNull(adapterPosition) ?: return
        val prevItem = currentList.getOrNull(adapterPosition - 1)
        val context = parent.context
        val topOffset = when (currentItem) {
            is ChatRoomMessageModel.Message.Send -> getChatRoomMessageSendItemTopOffset(prevItem = prevItem)
            is ChatRoomMessageModel.Message.Receive -> getChatRoomMessageReceiveItemTopOffset(
                prevItem = prevItem
            )
            is ChatRoomMessageModel.Time -> getChatRoomTimeItemTopOffset()
            is ChatRoomMessageModel.Notification -> getChatRoomNotificationItemTopOffset(prevItem = prevItem)
        }
        outRect.top = topOffset
        if (adapterPosition == adapter.itemCount - 1) outRect.bottom = context.dp2Px(18)
    }

    private fun getChatRoomMessageSendItemTopOffset(prevItem: ChatRoomMessageModel?) =
        when (prevItem) {
            is ChatRoomMessageModel.Message.Send -> 2
            is ChatRoomMessageModel.Message.Receive -> 13
            is ChatRoomMessageModel.Time -> 24
            is ChatRoomMessageModel.Notification -> 28
            else -> 18
        }


    private fun getChatRoomMessageReceiveItemTopOffset(prevItem: ChatRoomMessageModel?) =
        when (prevItem) {
            is ChatRoomMessageModel.Message.Send -> 13
            is ChatRoomMessageModel.Message.Receive -> 2
            is ChatRoomMessageModel.Time -> 24
            is ChatRoomMessageModel.Notification -> 28
            else -> 18
        }


    private fun getChatRoomTimeItemTopOffset() = 24

    private fun getChatRoomNotificationItemTopOffset(prevItem: ChatRoomMessageModel?) =
        when (prevItem) {
            is ChatRoomMessageModel.Time -> 8
            else -> 18
        }
}