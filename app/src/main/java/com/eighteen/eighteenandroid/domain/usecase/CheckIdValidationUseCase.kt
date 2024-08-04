package com.eighteen.eighteenandroid.domain.usecase

import com.eighteen.eighteenandroid.domain.repository.UserRepository
import javax.inject.Inject

class CheckIdValidationUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(id: String) = repository.postCheckIdValidation(id = id)

}