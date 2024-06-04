package com.eighteen.eighteenandroid.presentation.mediadetail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.eighteen.eighteenandroid.databinding.FragmentMediaDetailDialogBinding
import com.eighteen.eighteenandroid.presentation.BaseDialogFragment
import com.eighteen.eighteenandroid.presentation.common.media3.viewpager2.ViewPagerPlayerManager
import kotlinx.coroutines.launch

/**
 * add fragment로 열어야 하기 때문에 DialogFragment를 상속받음
 * JetPack navigation의 navigation graph에서 <dialog> 태그로 열어야 함
 */
//TODO 열고 닫을 때 애니메이션 추가, 미디어 포지션 공유할지 기획 문의 필요
class MediaDetailDialogFragment :
    BaseDialogFragment<FragmentMediaDetailDialogBinding>(FragmentMediaDetailDialogBinding::inflate) {

    private val mediaDetailViewModel by activityViewModels<MediaDetailViewModel>()

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
        initPlayerWrapper()
        bind {
            ivBtnBack.setOnClickListener {
                //TODO 네비게이션 back
            }
            ivBtnOption.setOnClickListener {
                //TODO 옵션메뉴
            }
        }
        bindMediaPager()
        initObservers()
    }

    private fun initPlayerWrapper() {
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
                            setCurrentItem(mediaDetailViewModel.selectedIndex, false)
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