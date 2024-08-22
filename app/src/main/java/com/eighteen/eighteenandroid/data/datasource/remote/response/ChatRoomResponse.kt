package com.eighteen.eighteenandroid.data.datasource.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

//TODO 채팅방 상대 name 필드 추가 문의
@JsonClass(generateAdapter = true)
data class ChatRoomResponse(
    @Json(name = "get_id")
    val chatRoomId: String,
    val senderNo: Int,
    val receiverNo: Int,
    val createdAt: Date,
    val updatedAt: Date,
    val unreadMessageCount: Int,
    val message: String,
    val messageCreatedAt: Date,
    val name: String? = null
)
