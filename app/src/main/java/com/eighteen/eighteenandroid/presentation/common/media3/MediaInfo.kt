package com.eighteen.eighteenandroid.presentation.common.media3

import androidx.media3.common.Player.REPEAT_MODE_ONE
import androidx.media3.common.Player.RepeatMode
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_FIT

@UnstableApi
data class MediaInfo(
    val id: String?,
    val mediaUrl: String,
    val mediaView: MediaView,
    @RepeatMode val repeatMode: Int = REPEAT_MODE_ONE,
    val resizeMode: Int = RESIZE_MODE_FIT
)
