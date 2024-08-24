package com.eighteen.eighteenandroid.data.datasource.remote.response

data class ProfileDetailResponse(
    val id: Int,
    val likeCount: Int,
    val nickName: String,
    val uniqueId: String,
    val birthDay: String,
    val location: String,
    val schoolName: String,
    val question: String?
)
