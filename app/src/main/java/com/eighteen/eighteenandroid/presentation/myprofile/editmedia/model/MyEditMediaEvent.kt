package com.eighteen.eighteenandroid.presentation.myprofile.editmedia.model

sealed interface MyEditMediaEvent {
    val uri: String?
    val isRep: Boolean

    data class Image(override val uri: String?, override val isRep: Boolean = false) :
        MyEditMediaEvent

    data class Video(override val uri: String?, override val isRep: Boolean = false) :
        MyEditMediaEvent
}
