package com.eighteen.eighteenandroid.presentation.auth.signup.enternickname

import androidx.lifecycle.ViewModel
import com.eighteen.eighteenandroid.presentation.auth.signup.enternickname.model.SignUpEnterNickNameModel
import com.eighteen.eighteenandroid.presentation.auth.signup.enternickname.model.SignUpEnterNickNameStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SignUpEnterNickNameViewModel : ViewModel() {
    private val _signUpEnterNickNameModel = MutableStateFlow(SignUpEnterNickNameModel())
    val signUpEnterNickNameModel: StateFlow<SignUpEnterNickNameModel> = _signUpEnterNickNameModel

    fun setInputText(input: String) {
        val status = checkStatus(input)
        _signUpEnterNickNameModel.value =
            signUpEnterNickNameModel.value.copy(inputString = input, status = status)
    }

    private fun checkStatus(input: String): SignUpEnterNickNameStatus {
        val isValidInput = Regex(REGEX_VALID_INPUT).matches(input)
        val isValidLength = Regex(REGEX_LENGTH).matches(input)
        return when {
            input.isEmpty() -> SignUpEnterNickNameStatus.None
            isValidInput && isValidLength -> SignUpEnterNickNameStatus.Success
            isValidInput.not() -> SignUpEnterNickNameStatus.Error.WrongInput
            else -> SignUpEnterNickNameStatus.Error.Length
        }
    }

    companion object {
        private const val REGEX_VALID_INPUT = """[가-힣a-zA-Z0-9]*"""
        private const val REGEX_LENGTH = """^.{2,8}"""
    }
}