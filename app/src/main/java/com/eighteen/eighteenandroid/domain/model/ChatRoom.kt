package com.eighteen.eighteenandroid.domain.model

import java.util.Date

data class ChatRoom(
    val chatRoomId: String,
    val senderNo: Int,
    val receiverNo: Int,
    val createdAt: Date,
    val updatedAt: Date,
    val unreadMessageCount: Int,
    val message: String,
    val messageCreatedAt: Date
)
