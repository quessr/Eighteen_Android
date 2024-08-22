package com.eighteen.eighteenandroid.domain.repository

import com.eighteen.eighteenandroid.domain.model.ChatMessage
import com.eighteen.eighteenandroid.domain.model.ChatRoom

interface ChatRepository {
    suspend fun requestChatMessages(
        chatRoomId: String,
        requestTime: String
    ): Result<List<ChatMessage>>

    suspend fun requestChatRooms(senderNo: Int): Result<List<ChatRoom>>
}