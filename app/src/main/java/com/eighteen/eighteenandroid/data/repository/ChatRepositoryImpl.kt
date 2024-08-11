package com.eighteen.eighteenandroid.data.repository

import com.eighteen.eighteenandroid.data.datasource.remote.service.ChatService
import com.eighteen.eighteenandroid.data.mapper.ChatMapper.toChatMessage
import com.eighteen.eighteenandroid.data.mapper.ChatMapper.toChatRoom
import com.eighteen.eighteenandroid.data.mapper.mapper
import com.eighteen.eighteenandroid.domain.model.ChatMessage
import com.eighteen.eighteenandroid.domain.model.ChatRoom
import com.eighteen.eighteenandroid.domain.repository.ChatRepository
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(private val chatService: ChatService) :
    ChatRepository {
    override suspend fun requestChatMessages(
        chatRoomId: String,
        requestTime: String
    ): Result<List<ChatMessage>> = runCatching {
        chatService.getChatMessages(chatRoomId = chatRoomId, requestTime = requestTime).mapper {
            it.data?.map { chatResponse ->
                chatResponse.toChatMessage()
            } ?: emptyList()
        }
    }


    override suspend fun requestChatRooms(senderNo: Int): Result<List<ChatRoom>> = runCatching {
        chatService.getChatRooms(senderNo = senderNo).mapper {
            it.data?.map { chatRoomResponse ->
                chatRoomResponse.toChatRoom()
            } ?: emptyList()
        }
    }
}