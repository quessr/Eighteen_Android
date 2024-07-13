package com.eighteen.eighteenandroid.hiltsample.data.mapper

import com.eighteen.eighteenandroid.hiltsample.data.datasource.remote.response.HiltSamplePostResponse
import com.eighteen.eighteenandroid.hiltsample.domain.model.HiltSamplePost

object HiltSamplePostMapper {
    fun asHiltSamplePostUserCaseModel(hiltSamplePostResponse: HiltSamplePostResponse) =
        hiltSamplePostResponse.run {
            HiltSamplePost(
                userId = userId,
                id = id,
                title = title,
                body = body
            )
        }

}