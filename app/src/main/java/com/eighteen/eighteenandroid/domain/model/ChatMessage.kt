package com.eighteen.eighteenandroid.domain.model

import java.util.Date

data class ChatMessage(
    val senderNo: Int,
    val receiverNo: Int,
    val message: String,
    val createdAt: Date?,
    val updatedAt: Date?
)
