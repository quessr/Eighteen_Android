package com.eighteen.eighteenandroid.presentation.common.media3.viewpager2

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.widget.ViewPager2
import com.eighteen.eighteenandroid.presentation.common.getRecyclerViewOrNull
import com.eighteen.eighteenandroid.presentation.common.media3.PlayerManager

/**
 * ViewPager2와 ExoPlayer를 연결시켜는 class
 * PlayerManager를 상속받음
 * @param viewPager2 : ViewPagerMediaItem을 구현한 ViewHolder가 포함된 ViewPager2
 */
class ViewPagerPlayerManager(
    private val viewPager2: ViewPager2,
    lifecycleOwner: LifecycleOwner,
    context: Context
) : PlayerManager(lifecycleOwner, context) {

    private val viewPagerRecyclerView = viewPager2.getRecyclerViewOrNull()

    private val onPageChangedCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            findViewPagerMediaItem(position = position)?.let { mediaItem ->
                play(mediaItem.getMediaInfo())
            } ?: run {
                targetMediaInfo?.let {
                    detachPlayer(it)
                }
            }
        }
    }

    init {
        viewPager2.registerOnPageChangeCallback(onPageChangedCallback)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        viewPager2.unregisterOnPageChangeCallback(onPageChangedCallback)
        super.onDestroy(owner)
    }

    private fun findViewPagerMediaItem(position: Int) =
        viewPagerRecyclerView?.findViewHolderForAdapterPosition(position) as? ViewPagerMediaItem
}