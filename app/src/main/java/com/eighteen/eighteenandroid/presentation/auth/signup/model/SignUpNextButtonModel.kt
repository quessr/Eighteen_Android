package com.eighteen.eighteenandroid.presentation.auth.signup.model

import androidx.annotation.StringRes
import com.eighteen.eighteenandroid.R

data class SignUpNextButtonModel(
    val isVisible: Boolean = false,
    val isEnabled: Boolean = false,
    val size: Size = Size.NORMAL,
    @StringRes val textRes: Int = R.string.sign_up_next
) {
    enum class Size {
        FULL,
        NORMAL
    }
}
