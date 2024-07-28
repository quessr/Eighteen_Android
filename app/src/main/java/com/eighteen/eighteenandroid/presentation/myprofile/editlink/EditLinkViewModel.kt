package com.eighteen.eighteenandroid.presentation.myprofile.editlink

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eighteen.eighteenandroid.domain.model.SnsLink
import com.eighteen.eighteenandroid.domain.usecase.AddSnsLinkUseCase
import com.eighteen.eighteenandroid.domain.usecase.RemoveSnsLinkUseCase
import com.eighteen.eighteenandroid.presentation.common.ModelState
import com.eighteen.eighteenandroid.presentation.common.livedata.Event
import com.eighteen.eighteenandroid.presentation.myprofile.editlink.model.EditLinkPage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditLinkViewModel @Inject constructor(
    private val addSnsLinkUseCase: AddSnsLinkUseCase,
    private val removeSnsLinkUseCase: RemoveSnsLinkUseCase
) : ViewModel() {
    private val _editLinkPageStateFlow = MutableStateFlow(EditLinkPage.MAIN)
    val editLinkPageStateFlow = _editLinkPageStateFlow.asStateFlow()

    private val _removeLinkResultStateFlow =
        MutableStateFlow<ModelState<Event<Int>>>(ModelState.Empty())
    val removeLinkResultState = _removeLinkResultStateFlow.asStateFlow()

    private val _addLinkResultStateFlow =
        MutableStateFlow<ModelState<Event<SnsLink>>>(ModelState.Empty())
    val addLinkResultStateFlow = _addLinkResultStateFlow.asStateFlow()

    private var addSnsLinkJob: Job? = null
    private var removeSnsLinkJob: Job? = null

    fun moveAddPage() {
        _editLinkPageStateFlow.value = EditLinkPage.ADD
    }

    fun movePrevPage() {
        if (editLinkPageStateFlow.value == EditLinkPage.ADD) {
            _editLinkPageStateFlow.value = EditLinkPage.MAIN
        }
    }

    fun requestRemoveLink(idx: Int) {
        if (removeSnsLinkJob?.isCompleted == false) return
        removeSnsLinkJob = viewModelScope.launch {
            _removeLinkResultStateFlow.value = ModelState.Loading()
            removeSnsLinkUseCase.invoke(idx = idx).onSuccess {
                _removeLinkResultStateFlow.value = ModelState.Success(Event(idx))
            }.onFailure {
                _removeLinkResultStateFlow.value = ModelState.Error(throwable = it)
            }
        }
    }

    fun requestAddLink(snsLink: SnsLink) {
        if (addSnsLinkJob?.isCompleted == false) return
        addSnsLinkJob = viewModelScope.launch {
            _addLinkResultStateFlow.value = ModelState.Loading()
            addSnsLinkUseCase.invoke(snsLink = snsLink).onSuccess {
                _addLinkResultStateFlow.value = ModelState.Success(Event(it))
            }.onFailure {
                _addLinkResultStateFlow.value = ModelState.Error(throwable = it)
            }
        }
    }
}