package com.eighteen.eighteenandroid.domain.model

sealed interface Media {
    val url: String?
    data class Image(override val url: String?) : Media
    data class Video(override val url: String?) : Media
}