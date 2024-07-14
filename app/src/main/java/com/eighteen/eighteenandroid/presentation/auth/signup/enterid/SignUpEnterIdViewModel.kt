package com.eighteen.eighteenandroid.presentation.auth.signup.enterid

import androidx.lifecycle.ViewModel
import com.eighteen.eighteenandroid.presentation.auth.signup.enterid.model.SignUpEnterIdModel
import com.eighteen.eighteenandroid.presentation.auth.signup.enterid.model.SignUpEnterIdStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpEnterIdViewModel @Inject constructor() : ViewModel() {
    private val _signUpEnterIdModel = MutableStateFlow(SignUpEnterIdModel())
    val signUpEnterIdModel: StateFlow<SignUpEnterIdModel> = _signUpEnterIdModel

    fun setInputText(input: String) {
        val status = checkStatus(input)
        _signUpEnterIdModel.value =
            signUpEnterIdModel.value.copy(inputString = input, status = status)
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