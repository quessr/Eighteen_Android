package com.eighteen.eighteenandroid.presentation.mediadetail.model

sealed class MediaDetailMediaModel {
    abstract val id: String
    abstract val imageUrl: String?

    data class Image(override val id: String, override val imageUrl: String? = null) :
        MediaDetailMediaModel()

    data class Video(
        override val id: String,
        override val imageUrl: String? = null,
        val videoUrl: String? = null
    ) : MediaDetailMediaModel()
}
