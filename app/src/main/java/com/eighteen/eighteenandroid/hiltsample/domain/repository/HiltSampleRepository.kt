package com.eighteen.eighteenandroid.hiltsample.domain.repository

import com.eighteen.eighteenandroid.hiltsample.domain.model.HiltSamplePost

interface HiltSampleRepository {
    suspend fun fetchTestData(userId: String): Result<List<HiltSamplePost>>
}