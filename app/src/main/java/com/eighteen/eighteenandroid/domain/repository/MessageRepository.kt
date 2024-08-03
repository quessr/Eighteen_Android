package com.eighteen.eighteenandroid.domain.repository

interface MessageRepository {
    suspend fun postSendMessage(phoneNumber: String): Result<String?>
    suspend fun postConfirmMessage(phoneNumber: String, verificationCode: String): Result<String?>
}