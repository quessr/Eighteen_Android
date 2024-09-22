package com.eighteen.eighteenandroid.presentation.auth.signup.addmedias.model

import android.net.Uri
import androidx.fragment.app.Fragment
import com.eighteen.eighteenandroid.presentation.common.mediapicker.MediaPicker

class SignUpMediaPickerWrapper(fragment: Fragment) {
    private var callback: (Uri?) -> Unit = {}
    private val mediaPicker = MediaPicker(fragment) {
        callback.invoke(it)
    }

    fun openMediaPicker(callback: (Uri?) -> Unit) {
        this.callback = callback
        mediaPicker.openMediaPicker()
    }
}