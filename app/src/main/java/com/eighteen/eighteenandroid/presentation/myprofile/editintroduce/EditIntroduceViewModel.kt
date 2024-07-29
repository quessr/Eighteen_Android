package com.eighteen.eighteenandroid.presentation.myprofile.editintroduce

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eighteen.eighteenandroid.domain.model.Mbti
import com.eighteen.eighteenandroid.domain.model.createMbtiOrNull
import com.eighteen.eighteenandroid.domain.usecase.EditIntroduceUseCase
import com.eighteen.eighteenandroid.presentation.common.ModelState
import com.eighteen.eighteenandroid.presentation.common.livedata.Event
import com.eighteen.eighteenandroid.presentation.myprofile.editintroduce.model.EditIntroduceModel
import com.eighteen.eighteenandroid.presentation.myprofile.editintroduce.model.EditIntroducePage
import com.eighteen.eighteenandroid.presentation.myprofile.editintroduce.model.EditMbtiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditIntroduceViewModel @Inject constructor(private val editIntroduceUseCase: EditIntroduceUseCase) :
    ViewModel() {
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

    private val _editIntroduceResultStateFlow =
        MutableStateFlow<ModelState<Event<EditIntroduceModel>>>(ModelState.Empty())
    val editIntroduceResultStateFlow = _editIntroduceResultStateFlow.asStateFlow()

    private var requestEditIntroduceJob: Job? = null

    fun moveNextPage() {
        if (pageStateFlow.value == EditIntroducePage.MBTI) _pageStateFlow.value =
            EditIntroducePage.DESCIRPTION
    }

    fun movePrevPage() {
        if (pageStateFlow.value == EditIntroducePage.DESCIRPTION) _pageStateFlow.value =
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

    fun requestEditIntroduce(description: String) {
        if (requestEditIntroduceJob?.isCompleted == false) return
        requestEditIntroduceJob = viewModelScope.launch {
            _editIntroduceResultStateFlow.value = ModelState.Loading()
            val mbti = createMbtiOrNull(selectedMbtiType)
            editIntroduceUseCase.invoke(
                description = description,
                mbti = mbti
            ).onSuccess {
                _editIntroduceResultStateFlow.value = ModelState.Success(
                    Event(
                        EditIntroduceModel(
                            mbti = mbti,
                            description = description
                        )
                    )
                )
            }.onFailure {
                _editIntroduceResultStateFlow.value = ModelState.Error(throwable = it)
            }
        }
    }
}