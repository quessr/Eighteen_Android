package com.eighteen.eighteenandroid.presentation.auth.signup.addmedias.model

import android.graphics.Bitmap

sealed interface SignUpMediaItemModel {
    val isRef: Boolean
    fun areContentsTheSame(other: SignUpMediaItemModel): Boolean

    data class Empty(override val isRef: Boolean) : SignUpMediaItemModel {
        override fun areContentsTheSame(other: SignUpMediaItemModel) = other is Empty
    }

    data class Image(val imageBitmap: Bitmap, override val isRef: Boolean = false) :
        SignUpMediaItemModel {
        override fun areContentsTheSame(other: SignUpMediaItemModel): Boolean =
            other is Image && this.imageBitmap == other.imageBitmap
    }

    data class Video(val uriString: String, override val isRef: Boolean = false) :
        SignUpMediaItemModel {
        override fun areContentsTheSame(other: SignUpMediaItemModel): Boolean =
            other is Video && this.uriString == other.uriString
    }
}