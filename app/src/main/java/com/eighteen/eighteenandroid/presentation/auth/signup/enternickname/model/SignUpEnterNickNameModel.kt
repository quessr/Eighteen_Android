package com.eighteen.eighteenandroid.presentation.auth.signup.enternickname.model

data class SignUpEnterNickNameModel(
    val inputString: String = "",
    val status: SignUpEnterNickNameStatus = SignUpEnterNickNameStatus.None
)