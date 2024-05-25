package com.eighteen.eighteenandroid.presentation.mediadetail

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.eighteen.eighteenandroid.databinding.FragmentMediaDetailBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.eighteen.eighteenandroid.presentation.mediadetail.model.MediaDetailModel
import com.eighteen.eighteenandroid.presentation.util.getParcelableListOrNull
import kotlinx.coroutines.launch

class MediaDetailFragment :
    BaseFragment<FragmentMediaDetailBinding>(FragmentMediaDetailBinding::inflate) {

    private val medias
        get() = arguments?.getParcelableListOrNull(
            ARGUMENT_MEDIAS_KEY,
            MediaDetailModel::class.java
        )?.take(MAXIMUM_DISPLAY_MEDIAS_COUNT) ?: emptyList()

    private val mediaDetailViewModel by viewModels<MediaDetailViewModel>()

    override fun initView() {
        bind {

        }
        initObservers()
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mediaDetailViewModel.selectedIndexStateFlow.collect {

                }
            }
        }
    }

    companion object {
        fun newInstance(medias: List<MediaDetailModel>) = MediaDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList(ARGUMENT_MEDIAS_KEY, ArrayList(medias))
            }
        }

        //TODO 글로벌 변수화 고려
        private const val MAXIMUM_DISPLAY_MEDIAS_COUNT = 10
        private const val ARGUMENT_MEDIAS_KEY = "argument_medias_key"
    }
}