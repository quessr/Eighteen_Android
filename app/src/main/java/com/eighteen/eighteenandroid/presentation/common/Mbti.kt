package com.eighteen.eighteenandroid.presentation.common

import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.domain.model.Mbti

fun Mbti.getDescriptionStringRes() = when (this) {
    Mbti.EXTROVERSION -> R.string.mbti_extroversion
    Mbti.INTROVERSION -> R.string.mbti_introversion
    Mbti.FEELING -> R.string.mbti_feeling
    Mbti.INTUITION -> R.string.mbti_intuition
    Mbti.JUDGING -> R.string.mbti_judging
    Mbti.SENSING -> R.string.mbti_sensing
    Mbti.PERCEIVING -> R.string.mbti_perceiving
    Mbti.THINKING -> R.string.mbti_thinking
}