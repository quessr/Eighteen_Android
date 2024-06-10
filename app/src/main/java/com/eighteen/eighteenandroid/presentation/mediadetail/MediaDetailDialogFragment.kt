package com.eighteen.eighteenandroid.presentation.mediadetail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import androidx.core.view.doOnLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.eighteen.eighteenandroid.databinding.FragmentMediaDetailDialogBinding
import com.eighteen.eighteenandroid.presentation.BaseDialogFragment
import com.eighteen.eighteenandroid.presentation.common.media3.viewpager2.ViewPagerPlayerManager
import com.eighteen.eighteenandroid.presentation.common.showDialogFragment
import com.eighteen.eighteenandroid.presentation.common.showReportDialog
import com.eighteen.eighteenandroid.presentation.dialog.ReportDialogFragment
import kotlinx.coroutines.launch

/**
 * 미디어와 선택된 position을 공유하기 위해 호출하는 Fragment와 MediaDetailViewModel 공유 필요
 * Fragment.showDialogFragment로 직접 열어줘야 ViewModel공유 가능(parentFragment로 공유)
 */
//TODO 열고 닫을 때 애니메이션 추가, 미디어 포지션 공유할지 기획 문의 필요 + 기획에 따라 포지션 초기화 필요
class MediaDetailDialogFragment :
    BaseDialogFragment<FragmentMediaDetailDialogBinding>(FragmentMediaDetailDialogBinding::inflate) {

    private val mediaDetailViewModel by viewModels<MediaDetailViewModel>(ownerProducer = {
        parentFragment ?: this
    })

    private val mediaPageChangeCallback = object : OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            mediaDetailViewModel.selectedIndex = position
        }
    }

    private var viewPagerPlayerManager: ViewPagerPlayerManager? = null

    override fun onResume() {
        super.onResume()
        initDialogWindow()
    }

    private fun initDialogWindow() {
        dialog?.window?.run {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            //TODO 블러처리 리소스를 많이 잡아먹는 작업임 + 안드 12이상부터 작동(논의 필요)
//            addFlags(FLAG_BLUR_BEHIND)
//            attributes= attributes.apply{
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                    blurBehindRadius = 20
//                }
//            }
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
                    showReportDialog(it) {
                        showDialogFragment(ReportDialogFragment())
                    }
                }
            }
        }
        bindMediaPager()
        initObservers()
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

    private fun bindMediaPager() {
        bind {
            with(vpMedias) {
                adapter = MediaDetailPagerAdapter().apply {
                    registerOnPageChangeCallback(mediaPageChangeCallback)
                }
                offscreenPageLimit = MAXIMUM_VIEWPAGER_OFF_SCREEN_PAGE_LIMIT.coerceAtMost(
                    (MAXIMUM_DISPLAY_MEDIAS_COUNT + 1) / 2
                )
            }
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mediaDetailViewModel.mediasStateFlow.collect {
                    binding.vpMedias.run {
                        (adapter as? MediaDetailPagerAdapter)?.submitList(it) {
                            val lastPosition = mediaDetailViewModel.selectedIndex
                            doOnLayout {
                                setCurrentItem(lastPosition, false)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        binding.vpMedias.unregisterOnPageChangeCallback(mediaPageChangeCallback)
        viewPagerPlayerManager = null
        super.onDestroyView()
    }

    companion object {
        //TODO 글로벌 변수화 고려
        private const val MAXIMUM_DISPLAY_MEDIAS_COUNT = 10
        private const val MAXIMUM_VIEWPAGER_OFF_SCREEN_PAGE_LIMIT = 10
    }
}