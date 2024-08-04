package com.eighteen.eighteenandroid.domain.usecase

import com.eighteen.eighteenandroid.domain.repository.ChatRepository
import javax.inject.Inject

class EnterChatRoomUseCase @Inject constructor(private val repository: ChatRepository) {
    suspend operator fun invoke(chatRoomId: String, requestTime: String) =
        repository.requestEnterChatRoom(chatRoomId = chatRoomId, requestTime = requestTime)

}