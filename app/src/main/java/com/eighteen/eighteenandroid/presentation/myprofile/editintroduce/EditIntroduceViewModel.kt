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

    private val mbtis = listOf(
        Mbti.EXTROVERSION,
        Mbti.INTROVERSION,
        Mbti.SENSING,
        Mbti.INTUITION,
        Mbti.THINKING,
        Mbti.FEELING,
        Mbti.JUDGING,
        Mbti.PERCEIVING
    )
    private val _mbtiModelsStateFlow = MutableStateFlow(mbtis.map { EditMbtiModel(mbti = it) })
    val mbtiModelsStateFlow = _mbtiModelsStateFlow.asStateFlow()

    fun moveNextPage() {
        if (pageStateFlow.value == EditIntroducePage.MBTI) _pageStateFlow.value =
            EditIntroducePage.DESCIRPTION
    }

    fun movePrevPage() {
        if (pageStateFlow.value == EditIntroducePage.DESCIRPTION) _pageStateFlow.value =
            EditIntroducePage.MBTI
    }

    fun toggleMbtiSelected(mbti: Mbti) {
        _mbtiModelsStateFlow.value = _mbtiModelsStateFlow.value.toMutableList().apply {
            replaceAll { if (it.mbti == mbti) it.copy(isSelected = !it.isSelected) else it }
        }
    }
}