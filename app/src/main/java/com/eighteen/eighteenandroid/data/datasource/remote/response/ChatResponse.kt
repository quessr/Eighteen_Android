package com.eighteen.eighteenandroid.data.datasource.remote.response

import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class ChatResponse(
    val senderNo: Int,
    val receiverNo: Int,
    val message: String,
    val createdAt: Date?,
    val updatedAt: Date?
)
