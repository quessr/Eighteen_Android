package com.eighteen.eighteenandroid.presentation.mediaedit

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment

abstract class BaseEditMediaFragment<VB : ViewBinding>(bindingFactory: (LayoutInflater) -> VB) :
    BaseFragment<VB>(bindingFactory = bindingFactory) {

    companion object {
        const val EDIT_MEDIA_URI_ARGUMENT_KEY = "edit_media_uri_argument_key"
    }
}