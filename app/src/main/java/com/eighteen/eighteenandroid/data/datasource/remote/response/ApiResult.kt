package com.eighteen.eighteenandroid.data.datasource.remote.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResult<T>(
    val status: String?,
    val data: T?,
    val message: String?
)