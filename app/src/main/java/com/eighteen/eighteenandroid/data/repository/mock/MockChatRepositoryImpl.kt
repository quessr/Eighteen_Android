package com.eighteen.eighteenandroid.data.repository.mock

import com.eighteen.eighteenandroid.domain.model.ChatMessage
import com.eighteen.eighteenandroid.domain.model.ChatRoom
import com.eighteen.eighteenandroid.domain.repository.ChatRepository
import java.util.Date
import javax.inject.Inject

class MockChatRepositoryImpl @Inject constructor() : ChatRepository {
    override suspend fun requestChatMessages(
        chatRoomId: String,
        requestTime: String
    ): Result<List<ChatMessage>> = runCatching {
        List(50) {
            ChatMessage(
                senderNo = 1,
                receiverNo = 6,
                message = "message $it".repeat(it % 10),
                createdAt = Date(),
                updatedAt = Date()
            )
        }
    }


    override suspend fun requestChatRooms(senderNo: Int): Result<List<ChatRoom>> = runCatching {
        List(50) {
            ChatRoom("$it", 123, 124, Date(), Date(), it, "message $it", Date(), name = "$it")
        }
    }
}