package com.eighteen.eighteenandroid.domain.repository

import com.eighteen.eighteenandroid.domain.model.User

interface UserRepository {
    suspend fun fetchUserData(): Result<List<User>>
}