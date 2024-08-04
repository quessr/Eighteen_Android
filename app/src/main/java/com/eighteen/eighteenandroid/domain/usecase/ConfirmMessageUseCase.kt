package com.eighteen.eighteenandroid.domain.usecase

import com.eighteen.eighteenandroid.domain.repository.MessageRepository
import javax.inject.Inject

class ConfirmMessageUseCase @Inject constructor(private val repository: MessageRepository) {
    suspend operator fun invoke(phoneNumber: String, verificationCode: String) =
        repository.postConfirmMessage(
            phoneNumber = phoneNumber,
            verificationCode = verificationCode
        )

}