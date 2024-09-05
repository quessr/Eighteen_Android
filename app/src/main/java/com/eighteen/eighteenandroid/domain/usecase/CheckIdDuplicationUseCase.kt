package com.eighteen.eighteenandroid.domain.usecase

import com.eighteen.eighteenandroid.domain.repository.UserRepository
import javax.inject.Inject

class CheckIdDuplicationUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(uniqueId: String) =
        repository.checkIdDuplication(uniqueId = uniqueId)
}