package com.eighteen.eighteenandroid.data.datasource.remote.request

data class ConfirmMessageRequest(
    val phoneNumber: String,
    val verificationCode: String
)
