package com.eighteen.eighteenandroid.domain.model

data class LoginResultInfo(
    val accessToken: String,
    val refreshToken: String,
    val uniqueId: String
)
