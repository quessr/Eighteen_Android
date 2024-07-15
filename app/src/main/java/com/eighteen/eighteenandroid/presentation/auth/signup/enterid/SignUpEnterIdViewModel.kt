package com.eighteen.eighteenandroid.presentation.auth.signup.enterid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eighteen.eighteenandroid.domain.usecase.CheckIdValidationUseCase
import com.eighteen.eighteenandroid.presentation.auth.signup.enterid.model.SignUpEnterIdModel
import com.eighteen.eighteenandroid.presentation.auth.signup.enterid.model.SignUpEnterIdStatus
import com.eighteen.eighteenandroid.presentation.common.ModelState
import com.eighteen.eighteenandroid.presentation.common.livedata.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpEnterIdViewModel @Inject constructor(private val checkIdValidationUseCase: CheckIdValidationUseCase) :
    ViewModel() {
    private val _signUpEnterIdModel = MutableStateFlow(SignUpEnterIdModel())
    val signUpEnterIdModel: StateFlow<SignUpEnterIdModel> = _signUpEnterIdModel

    private val _checkIdValidationEventStateFlow = MutableStateFlow<ModelState<Event<Unit>>?>(null)
    val checkIdValidationEventStateFlow: StateFlow<ModelState<Event<Unit>>?> =
        _checkIdValidationEventStateFlow

    private var checkIdValidationJob: Job? = null

    fun setInputText(input: String) {
        val status = checkStatus(input)
        _signUpEnterIdModel.value =
            signUpEnterIdModel.value.copy(inputString = input, status = status)
    }

    fun checkIdValidation() {
        if (checkIdValidationJob?.isCompleted == false) return
        checkIdValidationJob = viewModelScope.launch {
            _checkIdValidationEventStateFlow.value = ModelState.Loading()
            checkIdValidationUseCase.invoke(id = signUpEnterIdModel.value.inputString).onSuccess {
                _checkIdValidationEventStateFlow.value = ModelState.Success(Event(Unit))
            }.onFailure {
                //TODO 중복응답 코드 받을 경우 id 모델 수정
//                _signUpEnterIdModel.value =
//                    signUpEnterIdModel.value.copy(status = SignUpEnterIdStatus.Error.Duplicated)
                _checkIdValidationEventStateFlow.value = ModelState.Error(throwable = it)
            }
        }
    }

    private fun checkStatus(input: String): SignUpEnterIdStatus {
        val isValidInput = Regex(REGEX_VALID_INPUT).matches(input)
        val isValidLength = Regex(REGEX_LENGTH).matches(input)
        return when {
            input.isEmpty() -> SignUpEnterIdStatus.None
            isValidInput && isValidLength -> SignUpEnterIdStatus.Success
            isValidInput.not() -> SignUpEnterIdStatus.Error.WrongInput
            else -> SignUpEnterIdStatus.Error.Length
        }
    }

    companion object {
        private const val REGEX_VALID_INPUT = """[a-z0-9]*"""
        private const val REGEX_LENGTH = """^.{4,11}"""
    }
}