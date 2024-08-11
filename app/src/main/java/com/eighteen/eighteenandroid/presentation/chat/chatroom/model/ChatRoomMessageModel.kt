package com.eighteen.eighteenandroid.presentation.chat.chatroom.model

import androidx.annotation.DrawableRes
import java.util.Date

sealed interface ChatRoomMessageModel {
    sealed interface Message : ChatRoomMessageModel {
        val message: String
        val messageTimeString: String
        val createdAt: Date?

        data class Send(
            override val message: String,
            override val messageTimeString: String,
            override val createdAt: Date?
        ) : Message

        data class Receive(
            override val message: String,
            override val messageTimeString: String,
            override val createdAt: Date?,
            val imageUrl: String
        ) : Message
    }

    data class Notification(@DrawableRes val iconDrawableRes: Int, val message: String) :
        ChatRoomMessageModel

    data class Time(val timeString: String) : ChatRoomMessageModel
}