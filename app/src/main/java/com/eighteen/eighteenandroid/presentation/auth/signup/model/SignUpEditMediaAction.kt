package com.eighteen.eighteenandroid.presentation.auth.signup.model

import android.net.Uri

sealed interface SignUpEditMediaAction {
    val uri: Uri

    class EditImage(override val uri: Uri) : SignUpEditMediaAction
    class EditVideo(override val uri: Uri) : SignUpEditMediaAction
}