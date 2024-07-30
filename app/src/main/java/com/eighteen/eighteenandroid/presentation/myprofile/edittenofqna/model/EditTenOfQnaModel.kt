package com.eighteen.eighteenandroid.presentation.myprofile.edittenofqna.model

import com.eighteen.eighteenandroid.domain.model.QnaType

sealed interface EditTenOfQnaModel {
    fun areItemsTheSame(other: EditTenOfQnaModel): Boolean
    fun areContentsTheSame(other: EditTenOfQnaModel): Boolean

    object Title : EditTenOfQnaModel {
        override fun areItemsTheSame(other: EditTenOfQnaModel): Boolean = other is Title

        override fun areContentsTheSame(other: EditTenOfQnaModel): Boolean = other is Title
    }

    object Add : EditTenOfQnaModel {
        override fun areItemsTheSame(other: EditTenOfQnaModel): Boolean = other is Add
        override fun areContentsTheSame(other: EditTenOfQnaModel): Boolean = other is Add

    }

    data class Input(val qna: QnaType) : EditTenOfQnaModel {
        override fun areItemsTheSame(other: EditTenOfQnaModel): Boolean = other is Input

        override fun areContentsTheSame(other: EditTenOfQnaModel): Boolean =
            other is Input && other.qna == this.qna
    }
}