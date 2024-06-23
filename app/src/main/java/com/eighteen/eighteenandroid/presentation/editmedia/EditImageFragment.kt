package com.eighteen.eighteenandroid.presentation.editmedia

import android.net.Uri
import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.databinding.FragmentEditImageBinding
import com.eighteen.eighteenandroid.presentation.common.getParcelableOrNull
import com.eighteen.eighteenandroid.presentation.common.imageloader.ImageLoader

class EditImageFragment :
    BaseEditMediaFragment<FragmentEditImageBinding>(FragmentEditImageBinding::inflate) {

    private val imageUri by lazy {
        arguments?.getParcelableOrNull(EDIT_MEDIA_URI_ARGUMENT_KEY, Uri::class.java)
    }

    override fun initView() {
        bind {
            ivBtnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            ImageLoader.get().loadUrl(icvCropView.targetImageView, imageUri)
        }
    }
}