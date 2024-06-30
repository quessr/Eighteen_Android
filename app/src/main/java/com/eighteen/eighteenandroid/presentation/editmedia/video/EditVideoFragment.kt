package com.eighteen.eighteenandroid.presentation.editmedia.video

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.media.MediaPlayer
import android.net.Uri
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.graphics.scale
import androidx.core.view.doOnLayout
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentEditVideoBinding
import com.eighteen.eighteenandroid.presentation.common.getVideoThumbnailFromUri
import com.eighteen.eighteenandroid.presentation.common.media3.MediaInfo
import com.eighteen.eighteenandroid.presentation.common.media3.PlayerManager
import com.eighteen.eighteenandroid.presentation.editmedia.BaseEditMediaFragment
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class EditVideoFragment :
    BaseEditMediaFragment<FragmentEditVideoBinding>(FragmentEditVideoBinding::inflate) {
    private var playerManager: PlayerManager? = null

    private var timelineProgressUpdateJob: Job? = null
    override fun initView() {
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
                    setVideo(uriString = it)
                }
            }
        }
    }

    private fun initPlayerManager() {
        playerManager = context?.let {
            PlayerManager(lifecycleOwner = viewLifecycleOwner, context = it)
        }
    }

    private fun setVideo(uriString: String) {
        val mediaInfo = MediaInfo(id = uriString, mediaUrl = uriString, mediaView = binding.mvVideo)
        playerManager?.play(mediaInfo = mediaInfo)
        initTimelineImages(uriString = uriString)
        timelineProgressUpdateJob?.cancel()
        timelineProgressUpdateJob = viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                while (true) {
                    delay(TIMELINE_UPDATE_JOB_MIL_SEC)
                    updateTimelineProgress()
                }
            }
        }
    }

    private fun initTimelineImages(uriString: String) {
        val timelineLayout = binding.clTimeline
        val context = context ?: return
        val mediaPlayer = MediaPlayer.create(context, Uri.parse(uriString))
        val duration = mediaPlayer.duration
        mediaPlayer.release()
        timelineLayout.doOnLayout {
            val timelineImageHeight =
                resources.getDimensionPixelOffset(R.dimen.edit_media_video_timeline_height)
            val timelineImageCount = it.width / timelineImageHeight
            with(binding.ivTimelineImages) {
                updateLayoutParams {
                    width = timelineImageCount * timelineImageHeight
                }
                val times = List(timelineImageCount) { idx ->
                    (duration * idx / timelineImageCount).toLong() * 1000L
                }
                val thumbnailBitmaps =
                    getVideoThumbnailFromUri(context = context, uri = Uri.parse(uriString), times)
                val timelineBitmap = createTimelineBitmap(
                    bitmaps = thumbnailBitmaps,
                    width = it.width,
                    height = timelineImageHeight
                )
                thumbnailBitmaps.forEach { thumbnailBitmap -> thumbnailBitmap?.recycle() }
                background = BitmapDrawable(resources, timelineBitmap)
            }
        }
    }

    private fun createTimelineBitmap(bitmaps: List<Bitmap?>, width: Int, height: Int): Bitmap {
        val timelineBitmap =
            Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(timelineBitmap)
        bitmaps.forEachIndexed { idx, bitmap ->
            bitmap ?: return@forEachIndexed
            val left = (width * idx / bitmaps.size).toFloat()
            val scaledBitmap = bitmap.scale(width / bitmaps.size, height)
            canvas.drawBitmap(scaledBitmap, left, 0f, null)
        }
        return timelineBitmap
    }

    private fun updateTimelineProgress() {
        if (playerManager?.isPlaying != true) return
        val (currentPosition, duration) = (playerManager ?: return).run {
            currentPosition to duration
        }
        binding.ivProgress.updateLayoutParams<MarginLayoutParams> {
            marginStart = (binding.ivTimelineImages.width * currentPosition / duration).toInt()
        }
    }

    override fun onDestroyView() {
        playerManager = null
        super.onDestroyView()
    }

    companion object {
        private const val TIMELINE_UPDATE_JOB_MIL_SEC = 1000L
    }
}