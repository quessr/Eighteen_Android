package com.eighteen.eighteenandroid.presentation.mediadetail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.doOnLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.common.AppConfig
import com.eighteen.eighteenandroid.common.MEDIA_COUNT
import com.eighteen.eighteenandroid.databinding.FragmentMediaDetailDialogBinding
import com.eighteen.eighteenandroid.presentation.BaseDialogFragment
import com.eighteen.eighteenandroid.presentation.common.collectInLifecycle
import com.eighteen.eighteenandroid.presentation.common.getParcelableListOrNull
import com.eighteen.eighteenandroid.presentation.common.media3.viewpager2.ViewPagerPlayerManager
import com.eighteen.eighteenandroid.presentation.common.showReportSelectDialogBottom
import com.eighteen.eighteenandroid.presentation.mediadetail.model.MediaDetailMediaModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MediaDetailDialogFragment :
    BaseDialogFragment<FragmentMediaDetailDialogBinding>(FragmentMediaDetailDialogBinding::inflate) {

    private val mediaDetailViewModel by viewModels<MediaDetailViewModel>(factoryProducer = {
        MediaDetailViewModel.Factory(startPosition = startPosition)
    })

    private val mediaPageChangeCallback = object : OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            mediaDetailViewModel.selectedIndex = position
        }
    }

    private var viewPagerPlayerManager: ViewPagerPlayerManager? = null
    private val startPosition get() = arguments?.getInt(ARGUMENT_START_POSITION_KEY) ?: 0
    private val mediaModels
        get() = arguments?.getParcelableListOrNull(
            ARGUMENT_MEDIA_MODELS_KEY,
            MediaDetailMediaModel::class.java
        ) ?: emptyList()

    override fun onResume() {
        super.onResume()
        initDialogWindow()
    }

    private fun initDialogWindow() {
        dialog?.window?.run {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun initView() {
        initPlayerManager()
        bind {
            ivBtnClose.setOnClickListener {
                dismiss()
            }
            ivBtnOption.setOnClickListener {
                context?.let {
                    showReportSelectDialogBottom(
                        ivBtnOption,
                        onReportClicked = {
                            // 신고 다이얼로그 보여주기
                            // TODO. 유저 정보 필요
//                            showDialogFragment(ReportDialogFragment.newInstance(user))
                        },
                        onBlockClicked = {}
                    )
                }
            }
            ivBtnSound.setOnClickListener {
                viewPagerPlayerManager?.let {
                    setVolume(isVolumeOn = it.isVolumeOn.not())
                }
            }
        }
        bindMediaPager()
        initStateFlow()
    }

    private fun initPlayerManager() {
        viewPagerPlayerManager = context?.let {
            ViewPagerPlayerManager(
                viewPager2 = binding.vpMedias,
                lifecycleOwner = viewLifecycleOwner,
                context = it
            )
        }
    }

    private fun initStateFlow() {
        val context = context ?: return
        collectInLifecycle(
            AppConfig.getSoundOptionFlow(context)
                .stateIn(viewLifecycleOwner.lifecycleScope, SharingStarted.WhileSubscribed(), false)
        ) { isVolumeOn ->
            val drawableRes =
                if (isVolumeOn) R.drawable.ic_unmute else R.drawable.ic_mute
            binding.ivBtnSound.setImageResource(drawableRes)
            viewPagerPlayerManager?.setVolume(isVolumeOn = isVolumeOn)
        }
    }

    private fun bindMediaPager() {
        bind {
            with(vpMedias) {
                adapter = MediaDetailPagerAdapter().apply {
                    submitList(mediaModels) {
                        doOnLayout {
                            viewPagerPlayerManager?.playCurrent()
                        }
                    }
                }
                offscreenPageLimit = MEDIA_COUNT.coerceAtMost(5)
                setCurrentItem(mediaDetailViewModel.selectedIndex, false)
                registerOnPageChangeCallback(mediaPageChangeCallback)
            }
        }
    }

    private fun setVolume(isVolumeOn: Boolean) {
        val context = context ?: return
        viewLifecycleOwner.lifecycleScope.launch {
            AppConfig.setSoundOption(context, isVolumeOn)
        }
    }

    override fun onDestroyView() {
        binding.vpMedias.unregisterOnPageChangeCallback(mediaPageChangeCallback)
        viewPagerPlayerManager = null
        super.onDestroyView()
    }

    companion object {
        private const val ARGUMENT_START_POSITION_KEY = "argument_start_position_key"
        private const val ARGUMENT_MEDIA_MODELS_KEY = "argument_media_models_key"
        fun newInstance(
            startPosition: Int = 0,
            mediaModels: List<MediaDetailMediaModel>
        ) =
            MediaDetailDialogFragment().apply {
                arguments = bundleOf(
                    ARGUMENT_START_POSITION_KEY to startPosition,
                    ARGUMENT_MEDIA_MODELS_KEY to mediaModels
                )
            }
    }
}