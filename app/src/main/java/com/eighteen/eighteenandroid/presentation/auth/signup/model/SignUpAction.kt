package com.eighteen.eighteenandroid.presentation.auth.signup.model

sealed interface SignUpAction {
    object Next : SignUpAction
    object Prev : SignUpAction
    class OpenWebViewFragment(val url: String) : SignUpAction
}