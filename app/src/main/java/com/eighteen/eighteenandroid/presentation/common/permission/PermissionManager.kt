package com.eighteen.eighteenandroid.presentation.common.permission

import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class PermissionManager(activity: AppCompatActivity) {
    private var onGrantedCallback: (() -> Unit)? = null
    private var onDeniedCallback: (() -> Unit)? = null


    private val permissionsLauncher = activity.registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.any { !it.value }) {
            Log.e("PermissionManager", "권한 요청 거부")
            onDeniedCallback?.invoke()
        } else {
            onGrantedCallback?.invoke()
        }
        onGrantedCallback = null
        onDeniedCallback = null
    }

    fun requestPermissions(
        vararg permissions: String,
        onGranted: (() -> Unit)? = null,
        onDenied: (() -> Unit)? = null
    ) {
        onGrantedCallback = onGranted
        onDeniedCallback = onDenied
        permissionsLauncher.launch(permissions.toList().toTypedArray())
    }
}

