package com.eighteen.eighteenandroid.hiltsample.domain.usecase

import com.eighteen.eighteenandroid.hiltsample.domain.model.HiltSamplePost
import com.eighteen.eighteenandroid.hiltsample.domain.repository.HiltSampleRepository
import javax.inject.Inject

class HiltSampleUseCase @Inject constructor(private val repository: HiltSampleRepository) {
    suspend fun invoke(userId: String): Result<List<HiltSamplePost>> =
        repository.fetchTestData(userId = userId)

}