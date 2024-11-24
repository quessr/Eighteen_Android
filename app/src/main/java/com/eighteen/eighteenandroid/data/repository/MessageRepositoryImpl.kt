package com.eighteen.eighteenandroid.data.repository

import com.eighteen.eighteenandroid.data.datasource.remote.request.ConfirmMessageRequest
import com.eighteen.eighteenandroid.data.datasource.remote.request.SendMessageRequest
import com.eighteen.eighteenandroid.data.datasource.remote.service.MessageService
import com.eighteen.eighteenandroid.data.mapper.mapper
import com.eighteen.eighteenandroid.domain.repository.MessageRepository
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(private val messageService: MessageService) :
    MessageRepository {
    override suspend fun postSendMessage(phoneNumber: String) = runCatching {
        messageService.postSendMessage(sendMessageRequest = SendMessageRequest(phoneNumber))
            .mapper { it.data }
    }

    override suspend fun postConfirmMessage(
        phoneNumber: String,
        verificationCode: String
    ) = runCatching {
        val request =
            ConfirmMessageRequest(phoneNumber = phoneNumber, verificationCode = verificationCode)
        messageService.postConfirmMessage(confirmMessageRequest = request).mapper {
            it.data
        }
    }
}