package com.eighteen.eighteenandroid.presentation.editmedia.video

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.databinding.FragmentEditVideoBinding
import com.eighteen.eighteenandroid.presentation.common.media3.MediaInfo
import com.eighteen.eighteenandroid.presentation.common.media3.PlayerManager
import com.eighteen.eighteenandroid.presentation.editmedia.BaseEditMediaFragment
import kotlinx.coroutines.launch


class EditVideoFragment :
    BaseEditMediaFragment<FragmentEditVideoBinding>(FragmentEditVideoBinding::inflate) {
    private var playerManager: PlayerManager? = null
    override fun initView() {
        Log.d("TESTLOG", "${binding.llProgress.width}")
        initPlayerManager()
        bind {
            inHeader.ivBtnClose.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        initStateFlow()
    }

    private fun initStateFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                editMediaViewModel.mediaUriStringStateFlow.collect {
                    val mediaInfo = MediaInfo(id = it, mediaUrl = it, mediaView = binding.mvVideo)
                    playerManager?.play(mediaInfo = mediaInfo)
                }
            }
        }
    }

    private fun initPlayerManager() {
        playerManager = context?.let {
            PlayerManager(lifecycleOwner = viewLifecycleOwner, context = it)
        }
    }

    override fun onDestroyView() {
        playerManager = null
        super.onDestroyView()
    }
}