package com.eighteen.eighteenandroid.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eighteen.eighteenandroid.domain.model.LoginResultInfo
import com.eighteen.eighteenandroid.domain.model.Profile
import com.eighteen.eighteenandroid.domain.model.SnsLink
import com.eighteen.eighteenandroid.domain.usecase.MyProfileUseCase
import com.eighteen.eighteenandroid.presentation.common.ModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val myProfileUseCase: MyProfileUseCase) :
    ViewModel() {
    private val _myProfileStateFlow = MutableStateFlow<ModelState<Profile>>(ModelState.Empty())
    val myProfileStateFlow = _myProfileStateFlow.asStateFlow()

    //TODO token 모델 설계
    private var loginResultInfo: LoginResultInfo? = null

    private var loginJob: Job? = null

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
                async { myProfileUseCase.invoke(loginResultInfo.accessToken) }.await()

            myProfileResult.onSuccess {
                this@LoginViewModel.loginResultInfo = loginResultInfo
                _myProfileStateFlow.value = ModelState.Success(it)
            }.onFailure {
                _myProfileStateFlow.value = ModelState.Error(throwable = it)
            }
        }
    }

    fun addSnsLink(snsLink: SnsLink) {
        if (_myProfileStateFlow.value !is ModelState.Success) return
        _myProfileStateFlow.value = _myProfileStateFlow.value.run {
            val updatedSnsLinks = data?.snsLinks?.toMutableList()?.apply {
                add(snsLink)
            } ?: emptyList()
            val updatedProfile = data?.copy(snsLinks = updatedSnsLinks)
            ModelState.Success(updatedProfile)
        }
    }

    fun removeSnsLinkAt(idx: Int) {
        if (_myProfileStateFlow.value !is ModelState.Success) return
        _myProfileStateFlow.value = _myProfileStateFlow.value.run {
            val updatedSnsLinks = data?.snsLinks?.toMutableList()?.apply {
                removeAt(idx)
            } ?: emptyList()
            val updatedProfile = data?.copy(snsLinks = updatedSnsLinks)
            ModelState.Success(updatedProfile)
        }
    }
}