package com.eighteen.eighteenandroid.data.datasource.remote.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SendMessageResponse(
    override val status: String,
    override val data: String,
    override val message: String?
) : BaseResponse<String>