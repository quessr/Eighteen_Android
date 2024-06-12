package com.eighteen.eighteenandroid.presentation.common

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.eighteen.eighteenandroid.R
import com.google.android.material.chip.Chip

fun createChip(context: Context, tag: String): Chip {
    val chip = LayoutInflater.from(context).inflate(R.layout.tag_item, null) as Chip
    chip.text = tag
    chip.setChipBackgroundColorResource(android.R.color.white)

    // TODO: 추후 태그 문자열의 길이가 길어지는 경우도 대응해야함
    val params = ViewGroup.LayoutParams(200, ViewGroup.LayoutParams.WRAP_CONTENT)
    chip.layoutParams = params
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
