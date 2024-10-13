package com.eighteen.eighteenandroid.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eighteen.eighteenandroid.domain.model.LoginResultInfo
import com.eighteen.eighteenandroid.domain.model.Mbti
import com.eighteen.eighteenandroid.domain.model.Profile
import com.eighteen.eighteenandroid.domain.model.Qna
import com.eighteen.eighteenandroid.domain.model.School
import com.eighteen.eighteenandroid.domain.model.SnsInfo
import com.eighteen.eighteenandroid.domain.usecase.EditMyProfileUseCase
import com.eighteen.eighteenandroid.domain.usecase.GetMyProfileUseCase
import com.eighteen.eighteenandroid.presentation.common.ModelState
import com.eighteen.eighteenandroid.presentation.common.livedata.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getMyProfileUseCase: GetMyProfileUseCase,
    private val editMyProfileUseCase: EditMyProfileUseCase
) : ViewModel() {
    private val _myProfileStateFlow = MutableStateFlow<ModelState<Profile>>(ModelState.Empty())
    val myProfileStateFlow = _myProfileStateFlow.asStateFlow()

    private val _editProfileEventStateFlow =
        MutableStateFlow<ModelState<Event<Unit>>>(ModelState.Empty())
    val editProfileEventStateFlow = _editProfileEventStateFlow.asStateFlow()

    //TODO token 모델 설계
    private var loginResultInfo: LoginResultInfo? = null

    private var loginJob: Job? = null
    private var editMyProfileJob: Job? = null

    init {
        //TODO test 임시 호출 코드 제거
        requestLogin()
    }

    fun requestLogin() {
        if (loginJob?.isCompleted == false) return
        loginJob = viewModelScope.launch {
            //TODO accessToken 발급
            //TODO 로그인 api 호출
            val loginResultInfo =
                async { LoginResultInfo("accessToken", "refreshToken", "uniqueId") }.await()

            val myProfileResult =
                async { getMyProfileUseCase.invoke(loginResultInfo.accessToken) }.await()

            myProfileResult.onSuccess {
                this@LoginViewModel.loginResultInfo = loginResultInfo
                _myProfileStateFlow.value = ModelState.Success(it)
            }.onFailure {
                _myProfileStateFlow.value = ModelState.Error(throwable = it)
            }
        }
    }

    fun requestEditNickName(nickName: String?) {
        requestEditProfile(nickName = nickName)
    }

    fun requestEditIntroductionMbti(introduction: String?, mbti: Mbti?) {
        requestEditProfile(introduction = introduction, mbti = mbti)
    }

    fun requestEditSchool(school: School?) {
        requestEditProfile(school = school)
    }

    fun requestEditSnsInfo(snsInfo: List<SnsInfo>?) {
        requestEditProfile(snsInfo = snsInfo)
    }

    fun requestEditQuestions(questions: List<Qna>?) {
        requestEditProfile(questions = questions)
    }

    //TODO 토큰 획득
    private fun requestEditProfile(
        nickName: String? = null,
        school: School? = null,
        snsInfo: List<SnsInfo>? = null,
        mbti: Mbti? = null,
        introduction: String? = null,
        questions: List<Qna>? = null
    ) {
        if (editMyProfileJob?.isCompleted == false) return
        viewModelScope.launch {
            myProfileStateFlow.value.data?.let { profile ->
                val nickNameParam = nickName ?: profile.nickName
                val schoolParam = school ?: profile.school
                val snsInfoParam = snsInfo ?: profile.snsInfoList
                val mbtiParam = mbti ?: profile.mbti
                val introductionParam = introduction ?: profile.introduction
                val questionsParam = questions ?: profile.qna
                editMyProfileUseCase.invoke(
                    accessToken = "",
                    nickName = nickNameParam,
                    school = schoolParam,
                    snsInfo = snsInfoParam,
                    mbti = mbtiParam,
                    introduction = introductionParam,
                    questions = questionsParam
                ).onSuccess {
                    _editProfileEventStateFlow.value = ModelState.Success()
                    if (_myProfileStateFlow.value.isSuccess()) {
                        _myProfileStateFlow.update {
                            ModelState.Success(
                                it.data?.copy(
                                    nickName = nickNameParam,
                                    school = schoolParam,
                                    snsInfoList = snsInfoParam,
                                    mbti = mbtiParam,
                                    introduction = introductionParam,
                                    qna = questionsParam
                                )
                            )
                        }
                    }
                }.onFailure {
                    _editProfileEventStateFlow.value = ModelState.Error(throwable = it)
                }
            }
        }
    }
}