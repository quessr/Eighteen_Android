package com.eighteen.eighteenandroid.presentation.model

import com.eighteen.eighteenandroid.domain.model.Profile

data class LoginModel(
    val accessToken: String,
    val refreshToken: String,
    val profile: Profile
)
