package com.eighteen.eighteenandroid.presentation.common.mediapicker

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

open class MediaPicker {
    private val pickMedia: ActivityResultLauncher<PickVisualMediaRequest>

    constructor(activity: AppCompatActivity, callback: (Uri?) -> Unit) {
        pickMedia =
            activity.registerForActivityResult(resultLauncher) { uri ->
                callback.invoke(uri)
            }
    }

    constructor(fragment: Fragment, callback: (Uri?) -> Unit) {
        pickMedia =
            fragment.registerForActivityResult(resultLauncher) { uri ->
                callback.invoke(uri)
            }
    }

    fun openMediaPicker() {
        pickMedia.launch(pickVisualMediaRequest)
    }

    companion object {
        private val pickVisualMediaRequest =
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
        private val resultLauncher = object : ActivityResultContracts.PickVisualMedia() {
            override fun createIntent(context: Context, input: PickVisualMediaRequest): Intent {
                return super.createIntent(context, input).apply {
                    addFlags(FLAG_ACTIVITY_SINGLE_TOP)
                }
            }
        }
    }
}