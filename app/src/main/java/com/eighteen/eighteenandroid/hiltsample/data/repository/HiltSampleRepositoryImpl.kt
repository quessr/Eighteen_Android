package com.eighteen.eighteenandroid.hiltsample.data.repository

import com.eighteen.eighteenandroid.data.mapper.mapper
import com.eighteen.eighteenandroid.hiltsample.data.datasource.remote.service.HiltSampleRemoteService
import com.eighteen.eighteenandroid.hiltsample.data.mapper.HiltSamplePostMapper
import com.eighteen.eighteenandroid.hiltsample.domain.model.HiltSamplePost
import com.eighteen.eighteenandroid.hiltsample.domain.repository.HiltSampleRepository

class HiltSampleRepositoryImpl(private val hiltSampleRemoteService: HiltSampleRemoteService) :
    HiltSampleRepository {
    override suspend fun fetchTestData(userId: String): Result<List<HiltSamplePost>> =
        runCatching {
            hiltSampleRemoteService.getData(userId = userId).mapper {
                it.map { hiltSamplePostDao ->
                    HiltSamplePostMapper.asHiltSamplePostUserCaseModel(hiltSamplePostDao)
                }
            }
        }
}