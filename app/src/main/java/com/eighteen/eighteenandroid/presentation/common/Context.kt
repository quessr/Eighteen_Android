package com.eighteen.eighteenandroid.presentation.common

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowInsets
import android.view.WindowManager

fun Context.dp2Px(dp: Float): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
        .toInt()

fun Context.dp2Px(dp: Int): Int = dp2Px(dp.toFloat())

fun Context.px2DpF(px: Int): Float = px / resources.displayMetrics.density

fun Context.px2Dp(px: Int): Int = px2DpF(px).toInt()

/** 화면 가로 길이 구하기 */
fun Context.getScreenWidth(): Int {
    val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics = wm.currentWindowMetrics
        val insets = windowMetrics.windowInsets
            .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
        windowMetrics.bounds.width() - insets.left - insets.right
    } else {
        val displayMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(displayMetrics)
        displayMetrics.widthPixels
    }
}