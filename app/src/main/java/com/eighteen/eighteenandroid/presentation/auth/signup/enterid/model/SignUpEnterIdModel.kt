package com.eighteen.eighteenandroid.presentation.auth.signup.enterid.model

data class SignUpEnterIdModel(
    val inputString: String = "",
    val status: SignUpEnterIdStatus = SignUpEnterIdStatus.None
)
