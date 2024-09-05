package com.eighteen.eighteenandroid.presentation.auth.signup.model

sealed interface SignUpEditMediaAction {
    val uriString: String
    val isRef: Boolean

    class EditImage(override val uriString: String, override val isRef: Boolean) :
        SignUpEditMediaAction

    class EditVideo(override val uriString: String, override val isRef: Boolean) :
        SignUpEditMediaAction
}