package com.eighteen.eighteenandroid.presentation.auth.signup.model

import android.graphics.Bitmap

sealed interface SignUpMedia {
    data class Image(val imageBitmap: Bitmap) : SignUpMedia
    data class Video(val uriString: String) : SignUpMedia
}