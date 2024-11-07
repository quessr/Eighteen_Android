package com.eighteen.eighteenandroid.presentation.badgedetail.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BadgeDetailModel(
    val imageUrl: String?,
    val badgeName: String,
    val badgeDescription: String
) : Parcelable
