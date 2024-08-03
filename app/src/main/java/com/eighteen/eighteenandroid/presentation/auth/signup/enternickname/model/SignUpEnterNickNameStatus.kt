package com.eighteen.eighteenandroid.presentation.auth.signup.enternickname.model

import androidx.annotation.StringRes
import com.eighteen.eighteenandroid.R

sealed interface SignUpEnterNickNameStatus {
    interface Error : SignUpEnterNickNameStatus {
        @get:StringRes
        val errorStringRes: Int

        object WrongInput : Error {
            override val errorStringRes: Int = R.string.sign_up_enter_nickname_error_wrong_input
        }

        object Length : Error {
            override val errorStringRes: Int = R.string.sign_up_enter_nickname_error_length
        }
    }

    object None : SignUpEnterNickNameStatus
    object Success : SignUpEnterNickNameStatus
}