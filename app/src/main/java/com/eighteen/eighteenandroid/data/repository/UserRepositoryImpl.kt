package com.eighteen.eighteenandroid.data.repository

import com.eighteen.eighteenandroid.data.datasource.remote.request.SchoolRequest
import com.eighteen.eighteenandroid.data.datasource.remote.request.SignUpRequest
import com.eighteen.eighteenandroid.data.datasource.remote.service.UserService
import com.eighteen.eighteenandroid.data.mapper.UserMapper
import com.eighteen.eighteenandroid.data.mapper.mapper
import com.eighteen.eighteenandroid.domain.model.LoginResultInfo
import com.eighteen.eighteenandroid.domain.model.Mbti
import com.eighteen.eighteenandroid.domain.model.Media
import com.eighteen.eighteenandroid.domain.model.Profile
import com.eighteen.eighteenandroid.domain.model.Qna
import com.eighteen.eighteenandroid.domain.model.QnaType
import com.eighteen.eighteenandroid.domain.model.School
import com.eighteen.eighteenandroid.domain.model.SignUpInfo
import com.eighteen.eighteenandroid.domain.model.SnsLink
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

    //TODO 서버 api 적용
    override suspend fun getMyProfile(accessToken: String): Result<Profile> {
        val image1 =
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/BigBuckBunny.jpg"
        val image2 =
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ElephantsDream.jpg"
        val image3 =
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerBlazes.jpg"
        return Result.success(
            Profile(
                nickName = "nickname",
                school = School("school name", "address"),
                age = 17,
                badgeCount = 21,
                teenDescription = "teen description",
                medias = listOf(Media.Image(image1), Media.Image(image2), Media.Image(image3)),
                id = "id",
                description = "description",
                mbti = null,
                qna = List(10) { Qna(QnaType.values()[it], "answer$it") },
                snsLinks = listOf(SnsLink("https://www.google.co.kr", "test"))
            )
        )
    }

    //TODO api 호출 구현 필요
    override suspend fun addSnsLink(snsLink: SnsLink): Result<SnsLink> {
        return Result.success(snsLink)
    }

    //TODO api 호출 구현 필요
    override suspend fun removeSnsLink(idx: Int): Result<Unit> {
        return Result.success(Unit)
    }

    //TODO api 호출 구현 필요
    override suspend fun editIntroduce(
        description: String?,
        selectedMbti: Mbti?
    ): Result<Unit> {
        return Result.success(Unit)
    }
}