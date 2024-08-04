package com.eighteen.eighteenandroid.domain.usecase

import com.eighteen.eighteenandroid.domain.model.Mbti
import com.eighteen.eighteenandroid.domain.repository.UserRepository

class EditIntroduceUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(
        mbti: Mbti?,
        description: String?
    ): Result<Unit> {
        return repository.editIntroduce(description = description, selectedMbti = mbti)
    }
}