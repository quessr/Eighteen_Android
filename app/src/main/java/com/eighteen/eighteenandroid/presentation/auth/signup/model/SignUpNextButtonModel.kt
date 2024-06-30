package com.eighteen.eighteenandroid.presentation.auth.signup.model

import androidx.annotation.StringRes
import com.eighteen.eighteenandroid.R

data class SignUpNextButtonModel(
    val isVisible: Boolean = false,
    val isEnabled: Boolean = false,
    val size: Size = Size.NORMAL,
    val buttonText: ButtonText = ButtonText.NEXT
) {
    enum class Size {
        FULL,
        NORMAL
    }

    enum class ButtonText(@StringRes val strRes: Int) {
        NEXT(R.string.sign_up_next),
        PASS(R.string.sign_up_pass),
        START(R.string.sign_up_start)
    }
}
