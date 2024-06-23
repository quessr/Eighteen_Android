package com.eighteen.eighteenandroid.presentation.editmedia.image

import android.net.Uri
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentEditImageBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.eighteen.eighteenandroid.presentation.common.getParcelableOrNull
import com.eighteen.eighteenandroid.presentation.common.imageloader.ImageLoader
import com.eighteen.eighteenandroid.presentation.editmedia.EditMediaConfig.EDIT_MEDIA_POP_DESTINATION_ID_KEY
import com.eighteen.eighteenandroid.presentation.editmedia.EditMediaConfig.EDIT_MEDIA_URI_ARGUMENT_KEY

class EditImageFragment :
    BaseFragment<FragmentEditImageBinding>(FragmentEditImageBinding::inflate) {

    private val imageUri by lazy {
        arguments?.getParcelableOrNull(EDIT_MEDIA_URI_ARGUMENT_KEY, Uri::class.java)
    }

    private val popDestinationId by lazy {
        arguments?.getInt(EDIT_MEDIA_POP_DESTINATION_ID_KEY, -1)
    }

    override fun initView() {
        bind {
            ImageLoader.get().loadUrl(icvCropView.targetImageView, imageUri)
        }
        initHeader()
    }

    private fun initHeader() {
        with(binding.inHeader) {
            ivBtnClose.setOnClickListener {
                findNavController().popBackStack()
            }
            ivBtnCheck.setOnClickListener {
                val cropImageBitmap = binding.icvCropView.getCropAreaImageBitmap()
                val bundle = Bundle().apply {
                    putParcelable(CROP_IMAGE_ARGUMENT_KEY, cropImageBitmap)
                    popDestinationId?.let {
                        putInt(EDIT_MEDIA_POP_DESTINATION_ID_KEY, it)
                    }
                }
                findNavController().navigate(
                    R.id.action_fragmentEditImage_to_fragmentEditImageResult,
                    bundle
                )
            }
        }
    }

    companion object {
        const val CROP_IMAGE_ARGUMENT_KEY = "crop_image_argument_key"
    }
}