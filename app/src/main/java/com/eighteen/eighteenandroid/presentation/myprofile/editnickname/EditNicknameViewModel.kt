package com.eighteen.eighteenandroid.presentation.myprofile.editnickname

import androidx.lifecycle.ViewModel
import com.eighteen.eighteenandroid.presentation.common.model.nickname.NicknameModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EditNicknameViewModel : ViewModel() {
    private val _nicknameStatusStateFlow = MutableStateFlow(NicknameModel())
    val nicknameStatusStateFLow = _nicknameStatusStateFlow.asStateFlow()

    fun setNickName(nickname: String) {
        _nicknameStatusStateFlow.update {
            NicknameModel(inputString = nickname)
        }
    }
}