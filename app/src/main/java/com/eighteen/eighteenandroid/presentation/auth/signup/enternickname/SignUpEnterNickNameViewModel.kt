package com.eighteen.eighteenandroid.presentation.auth.signup.enternickname

import androidx.lifecycle.ViewModel
import com.eighteen.eighteenandroid.presentation.common.model.nickname.NicknameModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignUpEnterNickNameViewModel : ViewModel() {
    private val _nicknameModel = MutableStateFlow(NicknameModel())
    val nicknameModel: StateFlow<NicknameModel> =
        _nicknameModel.asStateFlow()

    fun setInputText(input: String) {
        _nicknameModel.value =
            nicknameModel.value.copy(inputString = input)
    }
}