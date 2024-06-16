package com.eighteen.eighteenandroid.presentation.auth.signup.addmedias.model

sealed interface SignUpMedia {
    data class Image(val imageUrl: String) : SignUpMedia
    data class Video(val thumbnailUrl: String) : SignUpMedia
    object Empty : SignUpMedia
}