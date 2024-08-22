package com.eighteen.eighteenandroid.data.mapper

import com.eighteen.eighteenandroid.data.datasource.remote.response.ChatResponse
import com.eighteen.eighteenandroid.data.datasource.remote.response.ChatRoomResponse
import com.eighteen.eighteenandroid.domain.model.ChatMessage
import com.eighteen.eighteenandroid.domain.model.ChatRoom

object ChatMapper {
    fun ChatResponse.toChatMessage() = ChatMessage(
        senderNo = senderNo,
        receiverNo = receiverNo,
        message = message,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

    fun ChatRoomResponse.toChatRoom() = ChatRoom(
        chatRoomId = chatRoomId,
        senderNo = senderNo,
        receiverNo = receiverNo,
        createdAt = createdAt,
        updatedAt = updatedAt,
        unreadMessageCount = unreadMessageCount,
        message = message,
        messageCreatedAt = messageCreatedAt,
        name = name ?: ""
    )
}