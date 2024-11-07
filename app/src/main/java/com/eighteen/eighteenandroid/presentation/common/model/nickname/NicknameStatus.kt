package com.eighteen.eighteenandroid.presentation.common.model.nickname

import androidx.annotation.StringRes
import com.eighteen.eighteenandroid.R

sealed interface NicknameStatus {
    interface Error : NicknameStatus {
        @get:StringRes
        val errorStringRes: Int

        object WrongInput : Error {
            override val errorStringRes: Int = R.string.nickname_error_wrong_input
        }

        object Length : Error {
            override val errorStringRes: Int = R.string.nickname_error_length
        }
    }

    object None : NicknameStatus
    object Success : NicknameStatus
}