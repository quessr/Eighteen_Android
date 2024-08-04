package com.eighteen.eighteenandroid.presentation.common

import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.domain.model.Mbti

fun Mbti.MbtiType.getDescriptionStringRes() = when (this) {
    Mbti.MbtiType.Energy.Extroversion -> R.string.mbti_extroversion
    Mbti.MbtiType.Energy.Introversion -> R.string.mbti_introversion
    Mbti.MbtiType.Decision.Feeling -> R.string.mbti_feeling
    Mbti.MbtiType.Information.Intuition -> R.string.mbti_intuition
    Mbti.MbtiType.Lifestyle.Judging -> R.string.mbti_judging
    Mbti.MbtiType.Information.Sensing -> R.string.mbti_sensing
    Mbti.MbtiType.Lifestyle.Perceiving -> R.string.mbti_perceiving
    Mbti.MbtiType.Decision.Thinking -> R.string.mbti_thinking
}