package com.eighteen.eighteenandroid.data.repository

import com.eighteen.eighteenandroid.data.datasource.remote.service.UserService
import com.eighteen.eighteenandroid.data.mapper.UserMapper
import com.eighteen.eighteenandroid.data.mapper.mapper
import com.eighteen.eighteenandroid.domain.model.User
import com.eighteen.eighteenandroid.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userService: UserService): UserRepository {
    override suspend fun fetchUserData(): Result<List<User>> =
        runCatching {
//            userService.getUserInfo().mapper {
//                it.map { userResponse ->
//                    UserMapper.asUserCaseModel(userResponse)
//                }
//            }

            listOf(
                User(
                    userImage = "https://image.blip.kr/v1/file/021ec61ff1c9936943383b84236a0e69",
                    userId = "1",
                    userName = "김 에스더",
                    userAge = "16",
                    userSchoolName = "서울 중학교",
                    tag = "운동"
                ),
                User(
                    userImage = "https://cdn.newsculture.press/news/photo/202308/529742_657577_5726.jpg",
                    userId = "2",
                    userName = "김 에스더",
                    userAge = "16",
                    userSchoolName = "부천 중학교",
                    tag = "스터디"
                ),
                User(
                    userImage = "https://mblogthumb-phinf.pstatic.net/MjAyMTEwMzFfMTY1/MDAxNjM1NjUzMTI2NjI3.xXYQteLLoWLKcR9YnXS0Hk_y-DInauMzF25g7FxlcScg.2Y-neBBMVoP2IhcwzX2Zy2HB2d8EnM_cY76FVLuk_1Yg.JPEG.ssun2415/IMG_4148.jpg?type=w800",
                    userId = "3",
                    userName = "김 에스더",
                    userAge = "16",
                    userSchoolName = "인천 중학교",
                    tag = "프로젝트"
                )
            )
        }
}