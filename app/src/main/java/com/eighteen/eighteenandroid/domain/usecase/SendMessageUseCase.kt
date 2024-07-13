package com.eighteen.eighteenandroid.domain.usecase

import com.eighteen.eighteenandroid.domain.repository.MessageRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(private val repository: MessageRepository) {
    suspend operator fun invoke(phoneNumber: String) =
        repository.postSendMessage(phoneNumber = phoneNumber)
}