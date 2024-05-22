package com.eighteen.eighteenandroid.hiltsample.data.mapper

import com.eighteen.eighteenandroid.hiltsample.data.datasource.remote.dao.HiltSamplePostDao
import com.eighteen.eighteenandroid.hiltsample.domain.model.HiltSamplePost

object HiltSamplePostMapper {
    fun asHiltSamplePostUserCaseModel(hiltSamplePostDao: HiltSamplePostDao) =
        hiltSamplePostDao.run {
            HiltSamplePost(
                userId = userId,
                id = id,
                title = title,
                body = body
            )
        }

}