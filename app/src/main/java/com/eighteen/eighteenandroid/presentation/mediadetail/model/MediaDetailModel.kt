package com.eighteen.eighteenandroid.presentation.mediadetail.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class MediaDetailModel : Parcelable {
    abstract val id: String?
    abstract val imageUrl: String?

    data class Image(override val id: String? = null, override val imageUrl: String? = null) :
        MediaDetailModel()

    data class Video(
        override val id: String? = null,
        override val imageUrl: String? = null,
        val videoUrl: String? = null
    ) : MediaDetailModel()
}
