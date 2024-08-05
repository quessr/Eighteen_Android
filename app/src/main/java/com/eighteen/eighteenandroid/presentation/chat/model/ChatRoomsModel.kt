package com.eighteen.eighteenandroid.presentation.chat.model

import com.eighteen.eighteenandroid.domain.model.ChatRoom

data class ChatRoomsModel(
    val keyword: String = "",
    val chatRooms: List<ChatRoom> = emptyList()
)
