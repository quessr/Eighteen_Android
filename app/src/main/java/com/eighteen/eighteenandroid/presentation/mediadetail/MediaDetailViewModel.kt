package com.eighteen.eighteenandroid.presentation.mediadetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eighteen.eighteenandroid.presentation.mediadetail.model.MediaDetailModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MediaDetailViewModel : ViewModel() {
    var selectedIndex: Int = 0
    var mediasStateFlow: StateFlow<List<MediaDetailModel>> = MutableStateFlow(emptyList())
        private set

    fun setMediasFlow(flow: Flow<List<MediaDetailModel>>) {
        mediasStateFlow = flow.stateIn(
            initialValue = emptyList(),
            started = SharingStarted.WhileSubscribed(), scope = viewModelScope
        )
    }
}