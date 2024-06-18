package com.eighteen.eighteenandroid.presentation.auth.signup.addmedias

import androidx.lifecycle.ViewModel
import com.eighteen.eighteenandroid.presentation.auth.signup.addmedias.model.SignUpMedia
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SignUpAddMediasViewModel : ViewModel() {
    private val _mediasStateFlow =
        MutableStateFlow<List<SignUpMedia>>(List(MINIMUM_DISPLAY_ITEM_COUNT) { SignUpMedia.Empty })
    val mediasStateFlow: StateFlow<List<SignUpMedia>> = _mediasStateFlow

    fun addMedia(media: SignUpMedia) {
        //TODO 미디어 추가하는 경우 구현
        _mediasStateFlow.value = _mediasStateFlow.value + media
    }

    //TODO 미디어 제거 추가 확인

    companion object {
        //TODO 최소 보여지는 아이템 개수(확인 후 수정)
        private const val MINIMUM_DISPLAY_ITEM_COUNT = 2
        private const val MAXIMUM_DISPLAY_ITEM_COUNT = 10
    }
}