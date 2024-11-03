package com.eighteen.eighteenandroid.domain.model

data class AuthToken(
    val accessToken: String? = null,
    val refreshToken: String? = null
)
