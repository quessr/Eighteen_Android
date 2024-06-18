package com.eighteen.eighteenandroid.presentation.mediaedit

import android.net.Uri
import android.util.Log
import com.eighteen.eighteenandroid.databinding.FragmentEditImageBinding
import com.eighteen.eighteenandroid.presentation.common.getParcelableOrNull

class EditImageFragment :
    BaseEditMediaFragment<FragmentEditImageBinding>(FragmentEditImageBinding::inflate) {

    private val imageUri by lazy {
        arguments?.getParcelableOrNull(EDIT_MEDIA_URI_ARGUMENT_KEY, Uri::class.java)
    }

    override fun initView() {
        Log.d("TESTLOG", "imageUri : $imageUri")
    }
}