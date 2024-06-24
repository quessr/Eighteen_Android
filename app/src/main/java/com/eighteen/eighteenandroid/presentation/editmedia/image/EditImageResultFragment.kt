package com.eighteen.eighteenandroid.presentation.editmedia.image

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.databinding.FragmentEditImageResultBinding
import com.eighteen.eighteenandroid.presentation.common.imageloader.ImageLoader
import com.eighteen.eighteenandroid.presentation.common.setNavigationResult
import com.eighteen.eighteenandroid.presentation.editmedia.BaseEditMediaFragment
import com.eighteen.eighteenandroid.presentation.editmedia.model.EditMediaResult
import kotlinx.coroutines.launch

class EditImageResultFragment :
    BaseEditMediaFragment<FragmentEditImageResultBinding>(FragmentEditImageResultBinding::inflate) {

    override fun initView() {
        initHeader()
        initStateFlow()
    }

    private fun initHeader() {
        with(binding.inHeader) {
            ivBtnClose.setOnClickListener {
                findNavController().popBackStack()
            }
            ivBtnCheck.setOnClickListener {
                editMediaViewModel.cropAreaBitmapStateFlow.value?.let {
                    //TODO 태그 추가
                    val editImageResult = EditMediaResult.Image(emptyList(), imageBitmap = it)
                    popDestinationId?.let { destinationId ->
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

    private fun initStateFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                editMediaViewModel.cropAreaBitmapStateFlow.collect { bitmap ->
                    val targetImageView = binding.ivResultImage
                    ImageLoader.get().loadBitmap(targetImageView, bitmap)
                }
            }
        }
    }
}