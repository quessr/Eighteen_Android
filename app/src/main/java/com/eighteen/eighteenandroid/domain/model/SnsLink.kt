package com.eighteen.eighteenandroid.domain.model

@Deprecated("링크 기획 변경(정해진 링크 사용)으로 인해 SnsInfo 사용")
data class SnsLink(
    val linkUrl: String,
    val name: String?
)
