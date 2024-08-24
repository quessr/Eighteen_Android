package com.eighteen.eighteenandroid.presentation.chat.chatroom.model

import androidx.annotation.DrawableRes
import java.util.Date

sealed interface ChatRoomMessageModel {
    fun areItemsTheSame(other: ChatRoomMessageModel): Boolean
    fun areContentsTheSame(other: ChatRoomMessageModel): Boolean
    sealed interface Message : ChatRoomMessageModel {
        val message: String
        val messageTimeString: String
        val createdAt: Date?

        data class Send(
            override val message: String,
            override val messageTimeString: String,
            override val createdAt: Date?
        ) : Message {
            override fun areItemsTheSame(other: ChatRoomMessageModel) =
                other is Send && this.message == other.message && this.createdAt == other.createdAt

            override fun areContentsTheSame(other: ChatRoomMessageModel) = this == other
        }


        data class Receive(
            override val message: String,
            override val messageTimeString: String,
            override val createdAt: Date?,
            val imageUrl: String
        ) : Message {
            override fun areItemsTheSame(other: ChatRoomMessageModel) =
                other is Receive && this.message == other.message && this.createdAt == other.createdAt

            override fun areContentsTheSame(other: ChatRoomMessageModel) = this == other
        }
    }

    data class Notification(@DrawableRes val iconDrawableRes: Int? = null, val message: String) :
        ChatRoomMessageModel {
        override fun areItemsTheSame(other: ChatRoomMessageModel): Boolean = this == other

        override fun areContentsTheSame(other: ChatRoomMessageModel): Boolean = this == other
    }

    data class Time(val timeString: String) : ChatRoomMessageModel {
        override fun areItemsTheSame(other: ChatRoomMessageModel): Boolean = this == other

        override fun areContentsTheSame(other: ChatRoomMessageModel): Boolean = this == other

    }
}