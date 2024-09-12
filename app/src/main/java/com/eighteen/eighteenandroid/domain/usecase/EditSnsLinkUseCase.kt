package com.eighteen.eighteenandroid.domain.usecase

import com.eighteen.eighteenandroid.domain.model.SnsInfo
import com.eighteen.eighteenandroid.domain.repository.UserRepository
import javax.inject.Inject

class EditSnsLinkUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(snsInfoList: List<SnsInfo>) =
        repository.editSnsLink(snsInfoList = snsInfoList)
}