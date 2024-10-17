package com.eighteen.eighteenandroid.domain.model

import androidx.annotation.DrawableRes
import com.eighteen.eighteenandroid.R

data class SnsInfo(val type: SnsType, val id: String) {
    enum class SnsType(@DrawableRes val iconDrawableRes: Int) {
        INSTAGRAM(R.drawable.ic_instagram),
        X(R.drawable.ic_x),
        TIKTOK(R.drawable.ic_tiktok),
        YOUTUBE(R.drawable.ic_youtube)
    }
}
