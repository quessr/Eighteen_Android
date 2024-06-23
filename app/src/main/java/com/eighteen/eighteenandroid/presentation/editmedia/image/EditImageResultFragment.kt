package com.eighteen.eighteenandroid.presentation.editmedia.image

import android.graphics.Bitmap
import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.databinding.FragmentEditImageResultBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.eighteen.eighteenandroid.presentation.common.getParcelableOrNull
import com.eighteen.eighteenandroid.presentation.common.setNavigationResult
import com.eighteen.eighteenandroid.presentation.editmedia.EditMediaConfig.EDIT_MEDIA_POP_DESTINATION_ID_KEY
import com.eighteen.eighteenandroid.presentation.editmedia.EditMediaConfig.EDIT_MEDIA_RESULT_KEY
import com.eighteen.eighteenandroid.presentation.editmedia.image.EditImageFragment.Companion.CROP_IMAGE_ARGUMENT_KEY
import com.eighteen.eighteenandroid.presentation.editmedia.model.EditMediaResult

class EditImageResultFragment :
    BaseFragment<FragmentEditImageResultBinding>(FragmentEditImageResultBinding::inflate) {

    private val bitmap
        get() = arguments?.getParcelableOrNull(
            CROP_IMAGE_ARGUMENT_KEY,
            Bitmap::class.java
        )

    private val popDestinationId by lazy {
        arguments?.getInt(EDIT_MEDIA_POP_DESTINATION_ID_KEY, -1)
    }

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