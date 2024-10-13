package com.eighteen.eighteenandroid.data.datasource.remote.request

import com.eighteen.eighteenandroid.data.datasource.remote.response.QuestionResponse
import com.eighteen.eighteenandroid.data.datasource.remote.response.SnsPlatformResponse

data class MyProfileRequest(
    val nickName: String?,
    val schoolData: SchoolData?,
    val snsPlatform: SnsPlatformResponse?,
    val mbti: String?,
    val introduction: String?,
    val questions: List<QuestionResponse>
) {
    //TODO school response 수정 필요
    data class SchoolData(
        val schoolName: String,
        val schoolLocation: String
    )
}
