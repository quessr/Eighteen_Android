package com.eighteen.eighteenandroid.data.repository.mock

import com.eighteen.eighteenandroid.common.DEFAULT_TIME_FORMAT_STRING
import com.eighteen.eighteenandroid.domain.model.ChatMessage
import com.eighteen.eighteenandroid.domain.model.ChatRoom
import com.eighteen.eighteenandroid.domain.repository.ChatRepository
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class MockChatRepositoryImpl @Inject constructor() : ChatRepository {
    override suspend fun requestChatMessages(
        chatRoomId: String,
        requestTime: String
    ): Result<List<ChatMessage>> = runCatching {
        delay(1200)
        val requestDate =
            SimpleDateFormat(DEFAULT_TIME_FORMAT_STRING, Locale.KOREA).parse(requestTime)
        val firstIndex = testData.indexOfFirst { (it.createdAt ?: Date()) <= requestDate }
        val resultList = testData.subList(firstIndex, minOf(testData.size, firstIndex + 30))
        return Result.success(resultList)
    }

    override suspend fun requestChatRooms(senderNo: Int): Result<List<ChatRoom>> = runCatching {
        List(50) {
            ChatRoom("$it", 123, 124, Date(), Date(), it, "message $it", Date(), name = "$it")
        }
    }

    private val calendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, 2000)
    }

    private val testData = List(340) {
        ChatMessage(
            senderNo = if (it % 3 == 0) 1 else 6,
            receiverNo = if (it % 3 == 0) 6 else 1,
            message = "message $it".repeat((it % 10) + 1),
            createdAt = calendar.time,
            updatedAt = Date()
        ).apply {
            calendar.add(
                Calendar.DATE,
                (0..37).random() / 17
            )
        }
    }.reversed()
}