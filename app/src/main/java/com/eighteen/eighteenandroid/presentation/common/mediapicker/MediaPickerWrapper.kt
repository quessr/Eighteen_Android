package com.eighteen.eighteenandroid.presentation.common.mediapicker

import android.net.Uri
import androidx.fragment.app.Fragment

class MediaPickerWrapper(fragment: Fragment) {
    private var callback: (Uri?) -> Unit = {}
    private val mediaPicker = MediaPicker(fragment) {
        callback.invoke(it)
    }

    fun openMediaPicker(callback: (Uri?) -> Unit) {
        this.callback = callback
        mediaPicker.openMediaPicker()
    }
}