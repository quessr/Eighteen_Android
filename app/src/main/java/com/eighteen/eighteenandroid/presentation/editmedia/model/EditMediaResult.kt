package com.eighteen.eighteenandroid.presentation.editmedia.model

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface EditMediaResult : Parcelable {
    @Parcelize
    data class Image(val imageBitmap: Bitmap) : EditMediaResult

    @Parcelize
    data class Video(val uriString: String) : EditMediaResult
}
