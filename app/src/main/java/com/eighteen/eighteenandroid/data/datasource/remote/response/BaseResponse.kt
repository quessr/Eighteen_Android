package com.eighteen.eighteenandroid.data.datasource.remote.response

interface BaseResponse<T> {
    val status: String?
    val data: T?
    val message: String?
}