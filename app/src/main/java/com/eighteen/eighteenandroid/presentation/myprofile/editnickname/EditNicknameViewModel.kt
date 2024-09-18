package com.eighteen.eighteenandroid.presentation.myprofile.editnickname

import androidx.lifecycle.ViewModel
import com.eighteen.eighteenandroid.presentation.common.model.nickname.NicknameModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EditNicknameViewModel @Inject constructor() : ViewModel() {
    private val _nicknameStatusStateFlow = MutableStateFlow(NicknameModel())
    val nicknameStatusStateFLow = _nicknameStatusStateFlow.asStateFlow()

    fun setNickName(nickname: String) {
        _nicknameStatusStateFlow.update {
            NicknameModel(inputString = nickname)
        }
    }
}