package com.eighteen.eighteenandroid.presentation.editmedia

import androidx.lifecycle.ViewModel
import com.eighteen.eighteenandroid.presentation.editmedia.model.CropViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EditMediaViewModel : ViewModel() {

    private val _mediaUriStringStateFlow = MutableStateFlow("")
    val mediaUriStringStateFlow: StateFlow<String> = _mediaUriStringStateFlow

    private val _imageCropViewStateFlow = MutableStateFlow<CropViewModel?>(null)
    val imageCropViewStateFlow: StateFlow<CropViewModel?> = _imageCropViewStateFlow
    fun setMediaUriString(uriString: String) {
        _mediaUriStringStateFlow.value = uriString
    }

    fun setImageCropModel(cropViewModel: CropViewModel) {
        _imageCropViewStateFlow.value = cropViewModel
    }


}