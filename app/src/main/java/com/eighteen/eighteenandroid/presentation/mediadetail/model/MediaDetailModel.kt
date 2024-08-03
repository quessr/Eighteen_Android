package com.eighteen.eighteenandroid.presentation.mediadetail.model

data class MediaDetailModel(
    val medias: List<MediaDetailMediaModel> = emptyList(),
    val isVolumeOn: Boolean = false
)
