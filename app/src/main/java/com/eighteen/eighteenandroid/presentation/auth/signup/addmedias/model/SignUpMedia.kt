package com.eighteen.eighteenandroid.presentation.auth.signup.addmedias.model

sealed interface SignUpMedia {
    fun isSameContent(other: SignUpMedia): Boolean
    data class Image(val imageUrl: String) : SignUpMedia {
        override fun isSameContent(other: SignUpMedia): Boolean =
            other is Image && this.imageUrl == other.imageUrl
    }

    data class Video(val thumbnailUrl: String) : SignUpMedia {
        override fun isSameContent(other: SignUpMedia): Boolean =
            other is Video && this.thumbnailUrl == other.thumbnailUrl
    }

    object Empty : SignUpMedia {
        override fun isSameContent(other: SignUpMedia) = other is Empty

    }

    object RefEmpty : SignUpMedia {
        override fun isSameContent(other: SignUpMedia): Boolean = other is RefEmpty
    }
}