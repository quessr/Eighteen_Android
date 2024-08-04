package com.eighteen.eighteenandroid.domain.usecase

import com.eighteen.eighteenandroid.domain.repository.UserRepository
import javax.inject.Inject

class MyProfileUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(accessToken: String) = repository.getMyProfile(accessToken)
}