package com.eighteen.eighteenandroid.presentation.mediadetail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.eighteen.eighteenandroid.databinding.FragmentMediaDetailDialogBinding
import com.eighteen.eighteenandroid.presentation.BaseDialogFragment
import com.eighteen.eighteenandroid.presentation.common.getParcelableListOrNull
import com.eighteen.eighteenandroid.presentation.common.media3.PlayerWrapper
import com.eighteen.eighteenandroid.presentation.mediadetail.model.MediaDetailModel
import kotlinx.coroutines.launch

/**
 * add fragment로 열어야 하기 때문에 DialogFragment를 상속받음
 * JetPack navigation의 navigation graph에서 <dialog> 태그로 열어야 함
 */
//TODO 열고 닫을 때 애니메이션 추가
class MediaDetailDialogFragment :
    BaseDialogFragment<FragmentMediaDetailDialogBinding>(FragmentMediaDetailDialogBinding::inflate) {

    private val medias
        get() = arguments?.getParcelableListOrNull(
            ARGUMENT_MEDIAS_KEY,
            MediaDetailModel::class.java
        )?.take(MAXIMUM_DISPLAY_MEDIAS_COUNT) ?: emptyList()

    private val mediaDetailViewModel by viewModels<MediaDetailViewModel>()

    private val mediaDetailPagerAdapter by lazy {
        MediaDetailPagerAdapter(mediaDetailModels = medias)
    }

    private val mediaPageChangeCallback = object : OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            mediaDetailViewModel.setSelectedIndex(index = position)
        }
    }

    private var playerWrapper: PlayerWrapper? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playerWrapper = context?.let {
            PlayerWrapper(viewLifecycleOwner, it)
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.run {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun initView() {
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

    private fun bindMediaPager() {
        bind {
            vpMedias.adapter = mediaDetailPagerAdapter
            vpMedias.registerOnPageChangeCallback(mediaPageChangeCallback)
        }
    }


    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mediaDetailViewModel.selectedIndexStateFlow.collect {
                    binding.vpMedias.currentItem = it
                }
            }
        }
    }

    override fun onDestroyView() {
        binding.vpMedias.unregisterOnPageChangeCallback(mediaPageChangeCallback)
        playerWrapper = null
        super.onDestroyView()
    }

    companion object {
        //TODO 글로벌 변수화 고려
        private const val MAXIMUM_DISPLAY_MEDIAS_COUNT = 10
        const val ARGUMENT_MEDIAS_KEY = "argument_medias_key"
    }
}