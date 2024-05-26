package com.eighteen.eighteenandroid.data.mapper

import com.eighteen.eighteenandroid.data.datasource.remote.dao.UserDao
import com.eighteen.eighteenandroid.domain.model.User

object UserMapper {
    fun asUserCaseModel(userDao: UserDao) =
        userDao.run {
            User(
                userImage = userImage,
                userId = userId,
                userName = name,
                userAge = age,
                userSchoolName = userSchoolName
            )
        }
}