package com.eighteen.eighteenandroid.data.repository.mock

import com.eighteen.eighteenandroid.domain.model.ChatMessage
import com.eighteen.eighteenandroid.domain.model.ChatRoom
import com.eighteen.eighteenandroid.domain.repository.ChatRepository
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class MockChatRepositoryImpl @Inject constructor() : ChatRepository {
    override suspend fun requestChatMessages(
        chatRoomId: String,
        requestTime: String
    ): Result<List<ChatMessage>> = runCatching {
        val calendar = Calendar.getInstance()
        List(50) {
            ChatMessage(
                senderNo = 1,
                receiverNo = 6,
                message = "message $it".repeat(it % 10),
                createdAt = calendar.time,
                updatedAt = Date()
            ).apply {
                calendar.add(
                    Calendar.DATE,
                    (0..37).random() / 17
                )
            }
        }
    }


    override suspend fun requestChatRooms(senderNo: Int): Result<List<ChatRoom>> = runCatching {
        List(50) {
            ChatRoom("$it", 123, 124, Date(), Date(), it, "message $it", Date(), name = "$it")
        }
    }
}