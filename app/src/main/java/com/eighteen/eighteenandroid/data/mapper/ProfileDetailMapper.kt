package com.eighteen.eighteenandroid.data.mapper

import com.eighteen.eighteenandroid.data.datasource.remote.response.ApiResult
import com.eighteen.eighteenandroid.data.datasource.remote.response.ProfileDetailResponse
import com.eighteen.eighteenandroid.domain.model.Profile
import com.eighteen.eighteenandroid.domain.model.School

object ProfileDetailMapper {
    fun asProfileDetailModel(profileDetailResponse: ApiResult<ProfileDetailResponse>) =
        Profile(
            nickName = profileDetailResponse.data?.nickName!!,
            age = 0,
            school = School(
                name = profileDetailResponse.data.schoolName,
                address = profileDetailResponse.data.location
            ),
            badgeCount = 0,
            teenDescription = null,
            medias = emptyList(),
            id = profileDetailResponse.data.uniqueId,
            snsLinks = emptyList(),
            mbti = null,
            description = null,
            qna = emptyList(),
            birth = null,
            introduction = null
        )
}