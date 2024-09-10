package com.eighteen.eighteenandroid.domain.model

//TODO snsLinks 제거(Deprecated), snsInfo 기본값 제거
data class Profile(
    val nickName: String,
    val age: Int,
    val school: School,
    val badgeCount: Int?,
    val teenDescription: String?,
    val medias: List<Media>,
    val id: String,
    val snsLinks: List<SnsLink> = emptyList(),
    val snsInfo: SnsInfo = SnsInfo(null, null, null, null),
    val mbti: Mbti?,
    val description: String?,
    val qna: List<Qna>
)
