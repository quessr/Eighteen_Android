package com.eighteen.eighteenandroid.presentation.chat.chatroom.model

import java.util.Date

data class ChatRoomMessagesModel(
    val date: Date = Date(),
    val messages: List<ChatRoomMessageModel> = emptyList(),
    val hasNextPage: Boolean = true
)