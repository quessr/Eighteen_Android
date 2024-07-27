package com.eighteen.eighteenandroid.presentation.myprofile.editlink

import androidx.lifecycle.ViewModel
import com.eighteen.eighteenandroid.presentation.myprofile.editlink.model.EditLinkPage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class EditLinkViewModel : ViewModel() {
    private val _editLinkPageStateFlow = MutableStateFlow(EditLinkPage.MAIN)
    val editLinkPageStateFlow = _editLinkPageStateFlow.asStateFlow()

    fun moveAddPage() {
        _editLinkPageStateFlow.value = EditLinkPage.ADD
    }

    fun movePrevPage() {
        if (editLinkPageStateFlow.value == EditLinkPage.ADD) {
            _editLinkPageStateFlow.value = EditLinkPage.MAIN
        }
    }
}