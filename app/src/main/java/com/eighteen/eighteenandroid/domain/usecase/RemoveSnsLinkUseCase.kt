package com.eighteen.eighteenandroid.domain.usecase

import com.eighteen.eighteenandroid.domain.repository.UserRepository
import javax.inject.Inject

class RemoveSnsLinkUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(idx: Int) = repository.removeSnsLink(idx = idx)
}