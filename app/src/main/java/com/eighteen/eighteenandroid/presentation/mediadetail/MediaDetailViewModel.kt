package com.eighteen.eighteenandroid.presentation.mediadetail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MediaDetailViewModel : ViewModel() {

    private val _selectedIndexStateFlow =
        MutableStateFlow(0)
    val selectedIndexStateFlow: StateFlow<Int> = _selectedIndexStateFlow

    fun setSelectedIndex(index: Int) {
        if (selectedIndexStateFlow.value == index) return
        _selectedIndexStateFlow.value = index
    }
}