package com.eighteen.eighteenandroid.presentation.common.media3

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import androidx.media3.common.Player
import com.eighteen.eighteenandroid.databinding.LayoutMediaViewBinding

class MediaView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = LayoutMediaViewBinding.inflate(LayoutInflater.from(context))

    val thumbnailView = binding.ivThumbnail
    val playerView = binding.pvMedia

    init {
        addView(binding.root)
    }

    fun setPlayer(player: Player?) {
        binding.pvMedia.player = player
    }
}