package com.eighteen.eighteenandroid.presentation.editmedia.image

import android.graphics.Bitmap
import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.databinding.FragmentEditImageResultBinding
import com.eighteen.eighteenandroid.presentation.common.getParcelableOrNull
import com.eighteen.eighteenandroid.presentation.common.setNavigationResult
import com.eighteen.eighteenandroid.presentation.editmedia.BaseEditMediaFragment
import com.eighteen.eighteenandroid.presentation.editmedia.image.EditImageFragment.Companion.CROP_IMAGE_ARGUMENT_KEY
import com.eighteen.eighteenandroid.presentation.editmedia.model.EditMediaResult

class EditImageResultFragment :
    BaseEditMediaFragment<FragmentEditImageResultBinding>(FragmentEditImageResultBinding::inflate) {

    private val bitmap
        get() = arguments?.getParcelableOrNull(
            CROP_IMAGE_ARGUMENT_KEY,
            Bitmap::class.java
        )

    override fun initView() {
        binding.ivResultImage.setImageBitmap(bitmap)
        initHeader()
    }

    private fun initHeader() {
        with(binding.inHeader) {
            ivBtnClose.setOnClickListener {
                findNavController().popBackStack()
            }
            ivBtnCheck.setOnClickListener {
                bitmap?.let {
                    //TODO 태그 추가
                    val editImageResult = EditMediaResult.Image(emptyList(), imageBitmap = it)
                    popDestinationId?.let { destinationId ->
                        it.recycle()
                        setNavigationResult(
                            key = EDIT_MEDIA_RESULT_KEY,
                            result = editImageResult,
                            destinationId = destinationId
                        )
                        findNavController().popBackStack(
                            destinationId = destinationId,
                            inclusive = false
                        )
                    }
                }
            }
        }
    }
}