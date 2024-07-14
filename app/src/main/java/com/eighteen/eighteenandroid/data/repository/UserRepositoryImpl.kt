package com.eighteen.eighteenandroid.data.repository

import com.eighteen.eighteenandroid.data.datasource.remote.service.UserService
import com.eighteen.eighteenandroid.data.mapper.UserMapper
import com.eighteen.eighteenandroid.data.mapper.mapper
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
}