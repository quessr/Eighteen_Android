package com.eighteen.eighteenandroid.presentation.auth.signup.enterid.model

import androidx.annotation.StringRes
import com.eighteen.eighteenandroid.R

sealed interface SignUpEnterIdStatus {
    interface Error : SignUpEnterIdStatus {
        @get:StringRes
        val errorStringRes: Int

        object WrongInput : Error {
            override val errorStringRes: Int = R.string.sign_up_enter_id_error_wrong_input
        }

        object Length : Error {
            override val errorStringRes: Int = R.string.sign_up_enter_id_error_length
        }

        object Duplicated : Error {
            override val errorStringRes: Int = R.string.sign_up_enter_id_error_duplicated
        }
    }

    object None : SignUpEnterIdStatus
    object Success : SignUpEnterIdStatus
}