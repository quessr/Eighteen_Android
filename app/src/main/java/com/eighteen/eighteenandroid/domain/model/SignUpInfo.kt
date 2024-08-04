package com.eighteen.eighteenandroid.domain.model

//TODO 이미지, 동영상 정보 추가
data class SignUpInfo(
    val phoneNumber: String,
    val uniqueId: String,
    val nickName: String,
    val birthDay: String,
    val school: School,
)