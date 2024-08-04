package com.eighteen.eighteenandroid.presentation.myprofile.editintroduce.model

import com.eighteen.eighteenandroid.domain.model.Mbti

data class EditMbtiModel(val mbtiType: Mbti.MbtiType, val isSelected: Boolean = false)
