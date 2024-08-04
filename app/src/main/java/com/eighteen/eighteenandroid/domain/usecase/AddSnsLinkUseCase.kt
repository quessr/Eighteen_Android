package com.eighteen.eighteenandroid.domain.usecase

import com.eighteen.eighteenandroid.domain.model.SnsLink
import com.eighteen.eighteenandroid.domain.repository.UserRepository
import javax.inject.Inject

class AddSnsLinkUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(snsLink: SnsLink) = repository.addSnsLink(snsLink = snsLink)
}