package com.eighteen.eighteenandroid.presentation.mediadetail

import androidx.lifecycle.ViewModel
import com.eighteen.eighteenandroid.presentation.mediadetail.model.MediaDetailMediaModel
import com.eighteen.eighteenandroid.presentation.mediadetail.model.MediaDetailModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MediaDetailViewModel : ViewModel() {
    var selectedIndex: Int = 0
    private val _mediaDetailStateFlow = MutableStateFlow(MediaDetailModel())
    val mediaDetailStateFlow = _mediaDetailStateFlow.asStateFlow()

    fun setMedias(medias: List<MediaDetailMediaModel>) {
        _mediaDetailStateFlow.value = mediaDetailStateFlow.value.copy(medias = medias)
    }

    fun toggleVolume() {
        _mediaDetailStateFlow.value = mediaDetailStateFlow.value.run {
            copy(isVolumeOn = !isVolumeOn)
        }
    }
}