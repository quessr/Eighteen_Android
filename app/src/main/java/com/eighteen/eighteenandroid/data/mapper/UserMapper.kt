package com.eighteen.eighteenandroid.data.mapper

import com.eighteen.eighteenandroid.data.datasource.remote.response.UserResponse
import com.eighteen.eighteenandroid.domain.model.User

object UserMapper {
    fun asUserCaseModel(userResponse: UserResponse) =
        userResponse.run {
            User(
                userImage = userImage,
                userId = userId,
                userName = name,
                userAge = age,
                userSchoolName = userSchoolName,
                tag = tag
            )
        }
}