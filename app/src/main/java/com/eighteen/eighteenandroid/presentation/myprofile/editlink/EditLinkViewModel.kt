package com.eighteen.eighteenandroid.presentation.myprofile.editlink

import androidx.lifecycle.ViewModel
import com.eighteen.eighteenandroid.domain.model.SnsLink
import com.eighteen.eighteenandroid.presentation.common.ModelState
import com.eighteen.eighteenandroid.presentation.common.livedata.Event
import com.eighteen.eighteenandroid.presentation.myprofile.editlink.model.EditLinkPage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EditLinkViewModel @Inject constructor(): ViewModel() {
    private val _editLinkPageStateFlow = MutableStateFlow(EditLinkPage.MAIN)
    val editLinkPageStateFlow = _editLinkPageStateFlow.asStateFlow()

    private val _removeLinkResultStateFlow =
        MutableStateFlow<ModelState<Event<Int>>>(ModelState.Empty())
    val removeLinkResultState = _removeLinkResultStateFlow.asStateFlow()

    private val _addLinkResultStateFlow =
        MutableStateFlow<ModelState<Event<SnsLink>>>(ModelState.Empty())
    val addLinkResultStateFlow = _addLinkResultStateFlow.asStateFlow()

    fun moveAddPage() {
        _editLinkPageStateFlow.value = EditLinkPage.ADD
    }

    fun movePrevPage() {
        if (editLinkPageStateFlow.value == EditLinkPage.ADD) {
            _editLinkPageStateFlow.value = EditLinkPage.MAIN
        }
    }

    fun requestRemoveLink(idx: Int) {
        //TODO 링크 제거 api
    }

    fun requestAddLink(snsLink: SnsLink) {
        //TODO 링크 추가 api
    }
}