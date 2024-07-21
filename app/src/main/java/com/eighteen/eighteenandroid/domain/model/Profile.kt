package com.eighteen.eighteenandroid.domain.model

data class Profile(
    val nickName: String,
    val age: Int,
    val school: School,
    val medias: List<Media>,
    val id: String,
    val snsLinks: List<SnsLink>,
    val mbti: String,
    val description: String?,
    val qna: List<Qna>
)
