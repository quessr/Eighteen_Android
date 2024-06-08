package com.eighteen.eighteenandroid.presentation.auth.signup.model

import androidx.annotation.StringRes

data class SignUpNextButtonModel(
    val isVisible: Boolean,
    val isEnabled: Boolean,
    val size: Size,
    @StringRes val textRes: Int
) {
    enum class Size {
        FULL,
        NORMAL
    }
}
