package com.eighteen.eighteenandroid.presentation.common

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.eighteen.eighteenandroid.R
import com.google.android.material.chip.Chip

fun createChip(context: Context, tag: String): Chip {
    val chip = LayoutInflater.from(context).inflate(R.layout.item_main_tag_chip, null) as Chip
    chip.text = tag
    chip.setChipBackgroundColorResource(android.R.color.white)

    val params = ViewGroup.LayoutParams(context.dp2Px(78), ViewGroup.LayoutParams.WRAP_CONTENT)
    chip.layoutParams = params

    val dp24 = context.dp2Px(24)
    chip.setPadding(dp24, dp24, dp24, dp24)
    return chip
}

// Extension
fun Chip.setTagStyle(isBlackBackground: Boolean) {
    if (isBlackBackground) {
        setChipBackgroundColorResource(android.R.color.black)
        setTextColor(ContextCompat.getColor(context, android.R.color.white))
    } else {
        setChipBackgroundColorResource(android.R.color.white)
        setTextColor(ContextCompat.getColor(context, android.R.color.black))
    }
}
