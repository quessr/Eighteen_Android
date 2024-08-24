package com.eighteen.eighteenandroid.domain.repository

import com.eighteen.eighteenandroid.domain.model.LoginResultInfo
import com.eighteen.eighteenandroid.domain.model.Mbti
import com.eighteen.eighteenandroid.domain.model.Profile
import com.eighteen.eighteenandroid.domain.model.SignUpInfo
import com.eighteen.eighteenandroid.domain.model.SnsLink
import com.eighteen.eighteenandroid.domain.model.User

interface UserRepository {
    suspend fun fetchUserData(): Result<List<User>>
    suspend fun fetchUserDetailInfo(id: String): Result<Profile>
    suspend fun postCheckIdValidation(id: String): Result<Unit?>
    suspend fun postSignUp(signUpInfo: SignUpInfo): Result<LoginResultInfo>
    suspend fun getMyProfile(accessToken: String): Result<Profile>
    suspend fun addSnsLink(snsLink: SnsLink): Result<SnsLink>
    //TODO removeSnsLink request param 확인 필요
    suspend fun removeSnsLink(idx: Int): Result<Unit>
    suspend fun editIntroduce(description: String?, selectedMbti: Mbti? = null): Result<Unit>
}