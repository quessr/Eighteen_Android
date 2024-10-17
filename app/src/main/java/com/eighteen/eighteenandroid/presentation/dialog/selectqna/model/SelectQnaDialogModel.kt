package com.eighteen.eighteenandroid.presentation.dialog.selectqna.model

import android.os.Parcelable
import com.eighteen.eighteenandroid.domain.model.QnaType
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectQnaDialogModel(
    val qnaType: QnaType,
    val isEnabled: Boolean
) : Parcelable
