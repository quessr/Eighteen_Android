package com.eighteen.eighteenandroid.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eighteen.eighteenandroid.domain.model.LoginResultInfo
import com.eighteen.eighteenandroid.domain.usecase.MyProfileUseCase
import com.eighteen.eighteenandroid.presentation.common.ModelState
import com.eighteen.eighteenandroid.presentation.model.LoginModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val myProfileUseCase: MyProfileUseCase) :
    ViewModel() {

    private val _loginStateFlow = MutableStateFlow<ModelState<LoginModel>>(ModelState.Empty())
    val loginStateFlow = _loginStateFlow.asStateFlow()

    private var loginJob: Job? = null

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
                val loginModel = LoginModel(
                    accessToken = loginResultInfo.accessToken,
                    refreshToken = loginResultInfo.refreshToken,
                    profile = it
                )
                _loginStateFlow.value = ModelState.Success(loginModel)
            }.onFailure {
                _loginStateFlow.value = ModelState.Error(throwable = it)
            }
        }
    }
}