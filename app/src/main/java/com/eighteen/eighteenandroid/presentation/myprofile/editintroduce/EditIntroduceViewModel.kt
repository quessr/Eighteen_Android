package com.eighteen.eighteenandroid.presentation.myprofile.editintroduce

import androidx.lifecycle.ViewModel
import com.eighteen.eighteenandroid.domain.model.Mbti
import com.eighteen.eighteenandroid.presentation.myprofile.editintroduce.model.EditIntroducePage
import com.eighteen.eighteenandroid.presentation.myprofile.editintroduce.model.EditMbtiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class EditIntroduceViewModel : ViewModel() {
    private val _pageStateFlow = MutableStateFlow(EditIntroducePage.MBTI)
    val pageStateFlow = _pageStateFlow.asStateFlow()

    private val mbtiList = listOf(
        Mbti.MbtiType.Energy.Extroversion,
        Mbti.MbtiType.Energy.Introversion,
        Mbti.MbtiType.Information.Sensing,
        Mbti.MbtiType.Information.Intuition,
        Mbti.MbtiType.Decision.Thinking,
        Mbti.MbtiType.Decision.Feeling,
        Mbti.MbtiType.Lifestyle.Judging,
        Mbti.MbtiType.Lifestyle.Perceiving
    )
    private val _mbtiModelsStateFlow =
        MutableStateFlow(mbtiList.map { EditMbtiModel(mbtiType = it) })
    val mbtiModelsStateFlow = _mbtiModelsStateFlow.asStateFlow()

    val selectedMbtiType
        get() = mbtiModelsStateFlow.value.filter { it.isSelected }.map { it.mbtiType }

    fun moveNextPage() {
        if (pageStateFlow.value == EditIntroducePage.MBTI) _pageStateFlow.value =
            EditIntroducePage.INTRODUCTION
    }

    fun movePrevPage() {
        if (pageStateFlow.value == EditIntroducePage.INTRODUCTION) _pageStateFlow.value =
            EditIntroducePage.MBTI
    }

    fun toggleMbtiSelected(mbtiType: Mbti.MbtiType) {
        val idx = mbtiList.indexOf(mbtiType)
        val pairIdx = idx + if (idx % 2 == 0) 1 else -1
        val pairMbtiType = mbtiList.getOrNull(pairIdx) ?: return
        _mbtiModelsStateFlow.value = mbtiModelsStateFlow.value.toMutableList().apply {
            replaceAll {
                when (it.mbtiType) {
                    mbtiType -> it.copy(isSelected = !it.isSelected)
                    pairMbtiType -> it.copy(isSelected = false)
                    else -> it
                }
            }
        }
    }
}