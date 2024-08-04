package com.eighteen.eighteenandroid.presentation.editmedia.image

import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.common.safeLet
import com.eighteen.eighteenandroid.databinding.FragmentEditImageResultBinding
import com.eighteen.eighteenandroid.presentation.common.imageloader.ImageLoader
import com.eighteen.eighteenandroid.presentation.editmedia.BaseEditMediaFragment
import com.eighteen.eighteenandroid.presentation.editmedia.model.EditMediaResult
import kotlinx.coroutines.launch

class EditImageResultFragment :
    BaseEditMediaFragment<FragmentEditImageResultBinding>(FragmentEditImageResultBinding::inflate) {
    override fun initView() {
        bind {
            inHeader.ivBtnCheck.isVisible = false
            inHeader.ivBtnClose.setOnClickListener {
                editMediaViewModel.setCropResultBitmap(null)
                findNavController().popBackStack()
            }
            tvBtnComplete.setOnClickListener {
                val resultBitmap =
                    editMediaViewModel.cropResultBitmapStateFlow.value
                safeLet(popDestinationId, resultBitmap) { popDestinationId, bitmap ->
                    editMediaViewModel.setEditResultEvent(result = EditMediaResult.Image(imageBitmap = bitmap))
                    findNavController().popBackStack(popDestinationId, false)
                }
            }
        }
        initStateFlow()
    }

    private fun initStateFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                editMediaViewModel.cropResultBitmapStateFlow.collect {
                    ImageLoader.get().loadBitmap(binding.ivImage, it)
                }
            }
        }
    }
}