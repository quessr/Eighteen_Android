package com.eighteen.eighteenandroid.domain.usecase

import com.eighteen.eighteenandroid.domain.model.Profile
import com.eighteen.eighteenandroid.domain.repository.UserRepository
import javax.inject.Inject

class GetUserDetailInfoUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(id: String): Result<Profile> =
        repository.fetchUserDetailInfo(id = id)
}