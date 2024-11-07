package com.eighteen.eighteenandroid.domain.model

import java.util.Date

//TODO snsLinks 제거(Deprecated), snsInfo 기본값 제거
data class Profile(
    val nickName: String,
    @Deprecated("birth 사용 후 나이 변환")
    val age: Int,
    val birth: Date?,
    val school: School,
    val badgeCount: Int?,
    val teenDescription: String?,
    val medias: List<Media>,
    val id: String,
    val snsLinks: List<SnsLink> = emptyList(),
    val snsInfoList: List<SnsInfo> = emptyList(),
    val mbti: Mbti?,
    @Deprecated("introduciton으로 변경")
    val description: String?,
    val introduction: String?,
    val qna: List<Qna>
)
