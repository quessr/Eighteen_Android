package com.eighteen.eighteenandroid.presentation.myprofile.editintroduce

import androidx.lifecycle.ViewModel
import com.eighteen.eighteenandroid.domain.model.Mbti
import com.eighteen.eighteenandroid.presentation.myprofile.editintroduce.model.EditIntroducePage
import com.eighteen.eighteenandroid.presentation.myprofile.editintroduce.model.EditMbtiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

//TODO api 호출 추가
@HiltViewModel
class EditIntroduceViewModel @Inject constructor() : ViewModel() {
    private val _pageStateFlow = MutableStateFlow(EditIntroducePage.MBTI)
    val pageStateFlow = _pageStateFlow.asStateFlow()

    private val mbtiList = listOf(
        Mbti.EXTROVERSION,
        Mbti.INTROVERSION,
        Mbti.SENSING,
        Mbti.INTUITION,
        Mbti.THINKING,
        Mbti.FEELING,
        Mbti.JUDGING,
        Mbti.PERCEIVING
    )
    private val _mbtiModelsStateFlow = MutableStateFlow(mbtiList.map { EditMbtiModel(mbti = it) })
    val mbtiModelsStateFlow = _mbtiModelsStateFlow.asStateFlow()

    val selectedMbti get() = mbtiModelsStateFlow.value.filter { it.isSelected }

    fun moveNextPage() {
        if (pageStateFlow.value == EditIntroducePage.MBTI) _pageStateFlow.value =
            EditIntroducePage.DESCIRPTION
    }

    fun movePrevPage() {
        if (pageStateFlow.value == EditIntroducePage.DESCIRPTION) _pageStateFlow.value =
            EditIntroducePage.MBTI
    }

    fun toggleMbtiSelected(mbti: Mbti) {
        val idx = mbtiList.indexOf(mbti)
        val pairIdx = idx + if (idx % 2 == 0) 1 else -1
        val pairMbti = mbtiList.getOrNull(pairIdx) ?: return
        _mbtiModelsStateFlow.value = mbtiModelsStateFlow.value.toMutableList().apply {
            replaceAll {
                when (it.mbti) {
                    mbti -> it.copy(isSelected = !it.isSelected)
                    pairMbti -> it.copy(isSelected = false)
                    else -> it
                }
            }
        }
    }

    fun requestEditIntroduce(description: String) {
        //TODO api 호출
    }
}