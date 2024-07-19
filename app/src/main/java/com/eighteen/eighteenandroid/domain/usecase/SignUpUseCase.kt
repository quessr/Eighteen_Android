package com.eighteen.eighteenandroid.domain.usecase

import com.eighteen.eighteenandroid.domain.model.SignUpInfo
import com.eighteen.eighteenandroid.domain.repository.UserRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(signUpInfo: SignUpInfo) =
        repository.postSignUp(signUpInfo = signUpInfo)
}