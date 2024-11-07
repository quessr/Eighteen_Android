package com.eighteen.eighteenandroid.domain.repository

import com.eighteen.eighteenandroid.domain.model.LoginResultInfo
import com.eighteen.eighteenandroid.domain.model.Profile
import com.eighteen.eighteenandroid.domain.model.SignUpInfo
import com.eighteen.eighteenandroid.domain.model.User

interface UserRepository {
    suspend fun fetchUserData(): Result<List<User>>
    suspend fun fetchUserDetailInfo(id: String): Result<Profile>
    suspend fun postSignUp(signUpInfo: SignUpInfo): Result<LoginResultInfo>
    suspend fun checkIdDuplication(uniqueId: String): Result<Boolean>
}