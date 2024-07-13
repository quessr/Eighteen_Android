package com.eighteen.eighteenandroid.domain.repository

interface MessageRepository {
    suspend fun postSendMessage(phoneNumber: String): Result<String?>
}