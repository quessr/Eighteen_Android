package com.eighteen.eighteenandroid.presentation.common.media3

import android.content.Context
import android.util.Log
import androidx.annotation.CallSuper
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

/**
 * exoPlayer에서 실행되는 메소드들을 모아놓은 wrapper 클래스
 * @param lifecycleOwner : destroy 시점에 player를 release 하기 위해 지정하는 lifecycle
 * @param context : ExoPlayer Builder에서 사용하는 context
 */
//TODO 영상 주소 잘못됐거나 로딩 실패했을 경우 처리, 사운드 처리
open class PlayerManager(
    private val lifecycleOwner: LifecycleOwner,
    context: Context
) : DefaultLifecycleObserver {

    protected open val player = ExoPlayer.Builder(context).build()
    private val playingInfoMap = HashMap<String, PlayingInfo>()
    protected var targetMediaInfo: MediaInfo? = null
    //TODO 썸네일 뷰 visibility 조절
//    private val playerListener = object : Player.Listener {
//        override fun onPlaybackStateChanged(playbackState: Int) {
//            when (playbackState) {
//                Player.STATE_IDLE->{
//                    Log.d("TESTLOG","idle")
//                }
//                Player.STATE_BUFFERING->{
//                    Log.d("TESTLOG","buffering")
//                }
//                Player.STATE_READY->{
//                    Log.d("TESTLOG","ready")
//                    targetMediaInfo?.mediaView?.thumbnailView?.isVisible= false
//                }
//            }
//        }
//    }

    init {
        initLifecycle()
    }

    private fun initLifecycle() {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    override fun onResume(owner: LifecycleOwner) {
        player.play()
    }

    override fun onPause(owner: LifecycleOwner) {
        player.pause()
    }

    @CallSuper
    override fun onDestroy(owner: LifecycleOwner) {
        release()
    }

    open fun play(mediaInfo: MediaInfo) {
        targetMediaInfo?.takeIf { it != mediaInfo }?.let { targetMediaInfo ->
            detachPlayer(targetMediaInfo)
        }
        targetMediaInfo = mediaInfo
        if (mediaInfo.mediaUrl != null) {
            player.setMediaItem(MediaItem.fromUri(mediaInfo.mediaUrl))
        } else {
            //TODO 영상 주소가 없을 경우 처리
            Log.d("PlayerWrapper", "mediaUrl is null")
        }
        mediaInfo.id?.let { playingInfoMap[it] }?.let { playingInfo ->
            setPlayingInfo(playingInfo)
        }
        player.prepare()
        player.play()
        mediaInfo.mediaView.setPlayer(player)
    }

    open fun pause() {
        player.pause()
    }

    fun stop() {
        player.stop()
    }

    //기존에 있던 Player와 분리하고 id값이 있을 경우 재생정보 저장
    protected fun detachPlayer(mediaInfo: MediaInfo) {
        mediaInfo.id?.let {
            playingInfoMap[mediaInfo.id] = PlayingInfo(positionMs = player.currentPosition)
        }
        player.pause()
        mediaInfo.mediaView.setPlayer(null)
    }

    private fun setPlayingInfo(playingInfo: PlayingInfo) {
        player.seekTo(playingInfo.positionMs)
    }

    fun clearPlayingInfo() {
        playingInfoMap.clear()
    }

    fun removePlayingInfo(key: String) {
        playingInfoMap.remove(key)
    }

    private fun release() {
        targetMediaInfo = null
        player.release()
    }

    protected data class PlayingInfo(
        val positionMs: Long = 0L
    )
}