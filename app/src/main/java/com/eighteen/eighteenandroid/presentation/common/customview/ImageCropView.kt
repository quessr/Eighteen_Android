package com.eighteen.eighteenandroid.presentation.common.customview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import androidx.annotation.Px
import androidx.core.view.doOnLayout
import androidx.core.view.drawToBitmap
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams
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
    private val cropViewMinHeightPx = context.dp2Px(CROP_VIEW_MIN_HEIGHT_DP)

    @Px
    private val cropViewMinWidthPx = cropViewMinHeightPx / CROP_VIEW_WIDTH_HEIGHT_RATIO

    val targetImageView = binding.ivTargetImage

    init {
        addView(binding.root)
        initCropAreaView()
    }

    fun getCropAreaImageBitmap(): Bitmap {
        val bitmap = binding.ivTargetImage.drawToBitmap()
        val resizedBitmap = binding.ivCropArea.run {
            Bitmap.createBitmap(bitmap, x.toInt(), y.toInt(), width, height)
        }
        bitmap.recycle()
        return resizedBitmap
    }

    private fun initCropAreaView() = with(binding.ivCropArea) {
        binding.ivTargetImage.doOnLayout {
            updateLayoutParams<MarginLayoutParams> {
                val cropViewWidth =
                    it.width.coerceAtMost((it.height * CROP_VIEW_WIDTH_HEIGHT_RATIO).toInt())
                val cropViewHeight =
                    it.height.coerceAtMost((it.width / CROP_VIEW_WIDTH_HEIGHT_RATIO).toInt())
                val horizontalMargin = (it.width - cropViewWidth) / 2
                val verticalMargin = (it.height - cropViewHeight) / 2
                setMargins(horizontalMargin, verticalMargin, horizontalMargin, verticalMargin)
            }
        }
        setOnTouchListener(object : OnTouchListener {
            private var actionDownRawPoint = Point()
            private var actionDownMargins = Margins()
            private var clickedPosition: CropViewClickedPosition? = null
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
                        clickedPosition =
                            getClickedPosition(
                                motionEvent.run { Point(x.toInt(), y.toInt()) },
                                view
                            )
                    }
                    MotionEvent.ACTION_MOVE -> {
                        val diffX = motionEvent.rawX.toInt() - actionDownRawPoint.x
                        val diffY = motionEvent.rawY.toInt() - actionDownRawPoint.y
                        clickedPosition?.let {
                            updateMarginsCropArea(
                                diff = Point(diffX, diffY),
                                actionDownMargins = actionDownMargins,
                                clickedPosition = it
                            )
                        }
                    }
                }
                return true
            }
        })
    }

    private fun getClickedPosition(
        actionDownPoint: Point,
        cropView: View
    ): CropViewClickedPosition {
        val isClickedTopEdge = (0..cropViewEdgePx).contains(actionDownPoint.y)
        val isClickedBottomEdge =
            (cropView.height - cropViewEdgePx..cropView.height).contains(actionDownPoint.y)
        val isClickedStartEdge = (0..cropViewEdgePx).contains(actionDownPoint.x)
        val isClickedEndEdge =
            (cropView.width - cropViewEdgePx..cropView.width).contains(actionDownPoint.x)
        val isClickedTop = (0..cropView.height / 2).contains(actionDownPoint.y)
        val isClickedStart = (0..cropView.width / 2).contains(actionDownPoint.x)

        val clickedDirection = when {
            isClickedTopEdge -> CropViewClickedPosition.Direction.TOP
            isClickedBottomEdge -> CropViewClickedPosition.Direction.BOTTOM
            isClickedStartEdge -> CropViewClickedPosition.Direction.START
            isClickedEndEdge -> CropViewClickedPosition.Direction.END
            else -> return CropViewClickedPosition.Center
        }

        val secondDirection = when (clickedDirection) {
            CropViewClickedPosition.Direction.TOP, CropViewClickedPosition.Direction.BOTTOM -> if (isClickedStart) CropViewClickedPosition.Direction.START else CropViewClickedPosition.Direction.END
            CropViewClickedPosition.Direction.START, CropViewClickedPosition.Direction.END -> if (isClickedTop) CropViewClickedPosition.Direction.TOP else CropViewClickedPosition.Direction.BOTTOM
        }
        return CropViewClickedPosition.Edge(
            clickedEdge = clickedDirection,
            secondDirection = secondDirection
        )
    }


    private fun updateMarginsCropArea(
        diff: Point,
        actionDownMargins: Margins,
        clickedPosition: CropViewClickedPosition
    ) {
        if (clickedPosition is CropViewClickedPosition.Edge) {
            val minDiff = { direction: CropViewClickedPosition.Direction ->
                when (direction) {
                    CropViewClickedPosition.Direction.TOP -> actionDownMargins.top
                    CropViewClickedPosition.Direction.BOTTOM -> actionDownMargins.bottom
                    CropViewClickedPosition.Direction.START -> actionDownMargins.start
                    CropViewClickedPosition.Direction.END -> actionDownMargins.end
                } * -1
            }
            val maxDiff = { direction: CropViewClickedPosition.Direction ->
                when (direction) {
                    CropViewClickedPosition.Direction.TOP -> binding.ivTargetImage.height - actionDownMargins.top - actionDownMargins.bottom - cropViewMinHeightPx
                    CropViewClickedPosition.Direction.BOTTOM -> binding.ivTargetImage.height - actionDownMargins.bottom - actionDownMargins.top - cropViewMinHeightPx
                    CropViewClickedPosition.Direction.START -> binding.ivTargetImage.width - actionDownMargins.start - actionDownMargins.end - cropViewMinWidthPx
                    CropViewClickedPosition.Direction.END -> binding.ivTargetImage.width - actionDownMargins.end - actionDownMargins.start - cropViewMinWidthPx
                }.toInt()
            }
            val clickedMarginDiff = run {
                val targetDiff = when (clickedPosition.clickedEdge) {
                    CropViewClickedPosition.Direction.TOP -> diff.y
                    CropViewClickedPosition.Direction.BOTTOM -> -diff.y
                    CropViewClickedPosition.Direction.START -> diff.x
                    CropViewClickedPosition.Direction.END -> -diff.x
                }
                targetDiff.coerceIn(minDiff(clickedPosition.clickedEdge)..maxDiff(clickedPosition.clickedEdge))
            }


            val secondMarginDiff = run {
                val mulRatio = when (clickedPosition.secondDirection) {
                    CropViewClickedPosition.Direction.TOP, CropViewClickedPosition.Direction.BOTTOM -> 1 / CROP_VIEW_WIDTH_HEIGHT_RATIO
                    else -> CROP_VIEW_WIDTH_HEIGHT_RATIO
                }
                (clickedMarginDiff * mulRatio).toInt().coerceIn(
                    minDiff(clickedPosition.secondDirection)..maxDiff(
                        clickedPosition.secondDirection
                    )
                )
            }

            val resultClickedMarginDiff = run {
                val mulRatio = when (clickedPosition.clickedEdge) {
                    CropViewClickedPosition.Direction.TOP, CropViewClickedPosition.Direction.BOTTOM -> 1 / CROP_VIEW_WIDTH_HEIGHT_RATIO
                    else -> CROP_VIEW_WIDTH_HEIGHT_RATIO
                }
                (secondMarginDiff * mulRatio).toInt()
                    .coerceIn(minDiff(clickedPosition.clickedEdge)..maxDiff(clickedPosition.clickedEdge))
            }
            val validClickedEdgeMargins = run {
                var top = 0
                var bottom = 0
                var start = 0
                var end = 0
                when (clickedPosition.clickedEdge) {
                    CropViewClickedPosition.Direction.TOP -> top = 1
                    CropViewClickedPosition.Direction.BOTTOM -> bottom = 1
                    CropViewClickedPosition.Direction.START -> start = 1
                    CropViewClickedPosition.Direction.END -> end = 1
                }
                Margins(top, bottom, start, end)
            }

            val validSecondMargins = run {
                var top = 0
                var bottom = 0
                var start = 0
                var end = 0
                when (clickedPosition.secondDirection) {
                    CropViewClickedPosition.Direction.TOP -> top = 1
                    CropViewClickedPosition.Direction.BOTTOM -> bottom = 1
                    CropViewClickedPosition.Direction.START -> start = 1
                    CropViewClickedPosition.Direction.END -> end = 1
                }
                Margins(top, bottom, start, end)
            }

            val resultMargins =
                actionDownMargins + Margins(resultClickedMarginDiff) * validClickedEdgeMargins + Margins(
                    secondMarginDiff
                ) * validSecondMargins

            binding.ivCropArea.updateLayoutParams<MarginLayoutParams> {
                setMargins(
                    resultMargins.start,
                    resultMargins.top,
                    resultMargins.end,
                    resultMargins.bottom
                )
            }

        } else {
            var topMargin = actionDownMargins.top + diff.y
            var bottomMargin = actionDownMargins.bottom - diff.y
            var startMargin = actionDownMargins.start + diff.x
            var endMargin = actionDownMargins.end - diff.x
            binding.ivCropArea.run {
                updateLayoutParams<MarginLayoutParams> {
                    if (topMargin < 0 || bottomMargin < 0) {
                        topMargin = this@run.marginTop
                        bottomMargin = this@run.marginBottom
                    }
                    if (startMargin < 0 || endMargin < 0) {
                        startMargin = this@run.marginStart
                        endMargin = this@run.marginEnd
                    }
                    setMargins(startMargin, topMargin, endMargin, bottomMargin)
                }
            }
        }
    }

    private class Margins(
        val top: Int = 0,
        val bottom: Int = 0,
        val start: Int = 0,
        val end: Int = 0
    ) {
        constructor(all: Int) : this(all, all, all, all)

        operator fun plus(other: Margins) = Margins(
            top = this.top + other.top,
            bottom = this.bottom + other.bottom,
            start = this.start + other.start,
            end = this.end + other.end
        )

        operator fun times(other: Margins) = Margins(
            top = this.top * other.top,
            bottom = this.bottom * other.bottom,
            start = this.start * other.start,
            end = this.end * other.end
        )
    }

    private sealed interface CropViewClickedPosition {
        enum class Direction {
            TOP,
            BOTTOM,
            START,
            END
        }

        class Edge(val clickedEdge: Direction, val secondDirection: Direction) :
            CropViewClickedPosition

        object Center : CropViewClickedPosition
    }

    companion object {
        private const val CROP_VIEW_EDGE_DP = 20
        private const val CROP_VIEW_WIDTH_HEIGHT_RATIO = 360.0 / 448
        private const val CROP_VIEW_MIN_HEIGHT_DP = 100
    }
}