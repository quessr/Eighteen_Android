package com.eighteen.eighteenandroid.presentation.editmedia.image

import android.net.Uri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentEditImageBinding
import com.eighteen.eighteenandroid.presentation.common.imageloader.ImageLoader
import com.eighteen.eighteenandroid.presentation.editmedia.BaseEditMediaFragment
import kotlinx.coroutines.launch

class EditImageFragment :
    BaseEditMediaFragment<FragmentEditImageBinding>(FragmentEditImageBinding::inflate) {

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
                val resultBitmap = binding.icvCropView.getCropAreaImageBitmap()
                editMediaViewModel.setCropResultBitmap(resultBitmap)
                findNavController().navigate(R.id.action_fragmentEditImage_to_fragmentEditImageResult)
            }
        }
    }

    private fun initStateFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                editMediaViewModel.mediaUriStringStateFlow.collect { uriString ->
                    val targetImageView = binding.icvCropView.targetImageView
                    ImageLoader.get().loadUrl(targetImageView, Uri.parse(uriString))
                }
            }
        }
    }
}