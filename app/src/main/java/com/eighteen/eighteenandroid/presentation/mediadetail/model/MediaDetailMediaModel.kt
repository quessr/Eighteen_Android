package com.eighteen.eighteenandroid.presentation.mediadetail.model

sealed class MediaDetailMediaModel {
    abstract val id: String
    abstract val mediaUrl: String?

    data class Image(override val id: String, override val mediaUrl: String? = null) :
        MediaDetailMediaModel()

    data class Video(
        override val id: String,
        override val mediaUrl: String? = null,
    ) : MediaDetailMediaModel()
}
