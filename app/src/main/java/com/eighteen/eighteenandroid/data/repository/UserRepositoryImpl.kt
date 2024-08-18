package com.eighteen.eighteenandroid.data.repository

import com.eighteen.eighteenandroid.data.datasource.remote.request.SchoolRequest
import com.eighteen.eighteenandroid.data.datasource.remote.request.SignUpRequest
import com.eighteen.eighteenandroid.data.datasource.remote.service.UserService
import com.eighteen.eighteenandroid.data.mapper.ProfileDetailMapper
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

    override suspend fun fetchUserDetailInfo(id: String): Result<Profile> = runCatching {
        userService.postProfileDetailInfo(id).mapper { profileDetailResponse ->
            ProfileDetailMapper.asProfileDetailModel(profileDetailResponse = profileDetailResponse)
        }
//        val image1 =
//            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
//        val image2 =
//            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ElephantsDream.jpg"
//        val image3 =
//            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerBlazes.jpg"
//        return Result.success(
//            Profile(
//                nickName = "nickname",
//                school = School("school name", "address"),
//                age = 17,
//                badgeCount = 21,
//                teenDescription = "teen description",
//                medias = listOf(Media.Image(image1), Media.Image(image2), Media.Image(image3)),
//                id = "id",
//                description = "description",
//                mbti = null,
//                qna = List(10) { Qna(QnaType.values()[it], "answer$it") },
//                snsLinks = listOf(SnsLink("https://www.google.co.kr", "test"))
//            )
//        )
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