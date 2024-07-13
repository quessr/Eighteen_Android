package com.eighteen.eighteenandroid.presentation.auth.signup.enterphonenumber

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eighteen.eighteenandroid.domain.usecase.SendMessageUseCase
import com.eighteen.eighteenandroid.presentation.common.ModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpEnterPhoneNumberViewModel @Inject constructor(
    private val sendMessageUseCase: SendMessageUseCase
) : ViewModel() {
    private val _sendMessageResultStateFlow = MutableStateFlow<ModelState<String>?>(null)
    val sendMessageResultStateFlow: StateFlow<ModelState<String>?> = _sendMessageResultStateFlow

    private var requestSendMessageJob: Job? = null

    fun requestSendMessage(phoneNumber: String) {
        if (requestSendMessageJob?.isCompleted == false) return
        requestSendMessageJob = viewModelScope.launch {
            _sendMessageResultStateFlow.value = ModelState.Loading()
            sendMessageUseCase.invoke(phoneNumber = phoneNumber).onSuccess {
                _sendMessageResultStateFlow.value = ModelState.Success(phoneNumber)
            }.onFailure {
                _sendMessageResultStateFlow.value = ModelState.Error(throwable = it)
            }
        }
    }

    fun clear() {
        requestSendMessageJob?.cancel()
        _sendMessageResultStateFlow.value = null
    }
}