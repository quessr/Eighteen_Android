package com.eighteen.eighteenandroid.presentation.common.customview

import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import androidx.annotation.Px
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import com.eighteen.eighteenandroid.databinding.ViewImageCropBinding
import com.eighteen.eighteenandroid.presentation.common.dp2Px

class ImageCropView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = ViewImageCropBinding.inflate(LayoutInflater.from(context))

    @Px
    private val cropViewEdgePx = context.dp2Px(CROP_VIEW_EDGE_DP)

    @Px
    private val cropViewMinSizePx = context.dp2Px(CROP_VIEW_MIN_SIZE_DP)

    val targetImageView = binding.ivTargetImage

    init {
        addView(binding.root)
        initCropAreaView()
    }


    private fun initCropAreaView() = with(binding.ivCropArea) {
        setOnTouchListener(object : OnTouchListener {
            private var actionDownRawPoint = Point()
            private var actionDownMargins = Margins()
            private var clickedPositions: List<CropViewClickedPosition> = emptyList()
            override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {
                view ?: return true
                when (motionEvent?.action) {
                    MotionEvent.ACTION_UP -> {
                        performClick()
                    }
                    MotionEvent.ACTION_DOWN -> {
                        actionDownRawPoint = motionEvent.run { Point(rawX.toInt(), rawY.toInt()) }
                        actionDownMargins = view.layoutParams.run {
                            Margins(
                                marginTop,
                                marginBottom,
                                marginStart,
                                marginEnd
                            )
                        }
                        clickedPositions =
                            getClickedPositions(
                                motionEvent.run { Point(x.toInt(), y.toInt()) },
                                view
                            )
                    }
                    MotionEvent.ACTION_MOVE -> {
                        val diffX = motionEvent.rawX.toInt() - actionDownRawPoint.x
                        val diffY = motionEvent.rawY.toInt() - actionDownRawPoint.y
                        updateMarginsCropArea(
                            diff = Point(diffX, diffY),
                            actionDownMargins = actionDownMargins,
                            cropView = view,
                            clickedPositions = clickedPositions
                        )
                    }
                }
                return true
            }
        })
    }

    private fun getClickedPositions(
        actionDownPoint: Point,
        cropView: View
    ): List<CropViewClickedPosition> {
        val isClickedTopEdge = (0..cropViewEdgePx).contains(actionDownPoint.y)
        val isClickedBottomEdge =
            (cropView.height - cropViewEdgePx..cropView.height).contains(actionDownPoint.y)
        val isClickedStartEdge = (0..cropViewEdgePx).contains(actionDownPoint.x)
        val isClickedEndEdge =
            (cropView.width - cropViewEdgePx..cropView.width).contains(actionDownPoint.x)
        return mutableListOf<CropViewClickedPosition>().apply {
            if (isClickedTopEdge) add(CropViewClickedPosition.TOP_EDGE)
            if (isClickedBottomEdge) add(CropViewClickedPosition.BOTTOM_EDGE)
            if (isClickedStartEdge) add(CropViewClickedPosition.START_EDGE)
            if (isClickedEndEdge) add(CropViewClickedPosition.END_EDGE)
            if (this.isEmpty()) add(CropViewClickedPosition.CENTER)
        }
    }

    private fun updateMarginsCropArea(
        diff: Point,
        actionDownMargins: Margins,
        cropView: View,
        clickedPositions: List<CropViewClickedPosition>
    ) {
        val updatedTopMargin =
            actionDownMargins.top + if (clickedPositions.any { it == CropViewClickedPosition.TOP_EDGE || it == CropViewClickedPosition.CENTER }) diff.y else 0
        val updatedBottomMargin =
            actionDownMargins.bottom - if (clickedPositions.any { it == CropViewClickedPosition.BOTTOM_EDGE || it == CropViewClickedPosition.CENTER }) diff.y else 0
        val updatedStartMargin =
            actionDownMargins.start + if (clickedPositions.any { it == CropViewClickedPosition.START_EDGE || it == CropViewClickedPosition.CENTER }) diff.x else 0
        val updatedEndMargin =
            actionDownMargins.end - if (clickedPositions.any { it == CropViewClickedPosition.END_EDGE || it == CropViewClickedPosition.CENTER }) diff.x else 0
        cropView.layoutParams = (cropView.layoutParams as? MarginLayoutParams)?.apply {
            setMargins(updatedStartMargin, updatedTopMargin, updatedEndMargin, updatedBottomMargin)
        }
    }


    private data class Margins(
        val top: Int = 0,
        val bottom: Int = 0,
        val start: Int = 0,
        val end: Int = 0
    )

    private enum class CropViewClickedPosition {
        TOP_EDGE,
        BOTTOM_EDGE,
        START_EDGE,
        END_EDGE,
        CENTER
    }

    companion object {
        private const val CROP_VIEW_EDGE_DP = 20
        private const val CROP_VIEW_MIN_SIZE_DP = 100
    }
}