package com.eighteen.eighteenandroid.data.repository

import com.eighteen.eighteenandroid.data.datasource.remote.request.SchoolRequest
import com.eighteen.eighteenandroid.data.datasource.remote.request.SignUpRequest
import com.eighteen.eighteenandroid.data.datasource.remote.service.UserService
import com.eighteen.eighteenandroid.data.mapper.UserMapper
import com.eighteen.eighteenandroid.data.mapper.mapper
import com.eighteen.eighteenandroid.domain.model.LoginResultInfo
import com.eighteen.eighteenandroid.domain.model.SignUpInfo
import com.eighteen.eighteenandroid.domain.model.User
import com.eighteen.eighteenandroid.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userService: UserService) :
    UserRepository {
    override suspend fun fetchUserData(): Result<List<User>> =
        runCatching {
            userService.getUserInfo().mapper {
                it.map { userResponse ->
                    UserMapper.asUserCaseModel(userResponse)
                }
            }
        }

    override suspend fun postCheckIdValidation(id: String): Result<Unit?> {
        //TODO id 체크 api 추가
        return Result.success(Unit)
    }

    override suspend fun postSignUp(signUpInfo: SignUpInfo): Result<LoginResultInfo> = runCatching {
        val signUpRequest = signUpInfo.run {
            SignUpRequest(
                phoneNumber = phoneNumber,
                uniqueId = uniqueId,
                birthDay = birthDay,
                nickName = nickName,
                schoolData = SchoolRequest(
                    schoolName = school.name,
                    schoolLocation = school.address
                )
            )
        }
        //TODO 응답 값 수정(문의 중)
        userService.postSignUp(signUpRequest = signUpRequest).mapper {
            LoginResultInfo("", "", uniqueId = it.data?.uniqueId ?: "")
        }
    }
}