package com.eighteen.eighteenandroid.domain.usecase

import com.eighteen.eighteenandroid.domain.repository.ChatRepository
import javax.inject.Inject

class GetChatRoomsUseCase @Inject constructor(private val repository: ChatRepository) {
    suspend operator fun invoke(senderNo: Int) = repository.requestChatRooms(senderNo = senderNo)
}