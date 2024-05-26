package com.eighteen.eighteenandroid.domain.usecase

import com.eighteen.eighteenandroid.domain.model.User
import com.eighteen.eighteenandroid.domain.repository.UserRepository
import javax.inject.Inject

class UserUseCase @Inject constructor(private val repository: UserRepository) {
    suspend fun invoke(): Result<List<User>> =
        repository.fetchUserData()
}