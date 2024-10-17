package com.eighteen.eighteenandroid.presentation.myprofile.editlink

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eighteen.eighteenandroid.presentation.myprofile.editlink.model.EditLinkModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EditLinkViewModel(
    initInstagram: String,
    initX: String,
    initTiktok: String,
    initYoutube: String
) : ViewModel() {
    private val _editLinkModelStateFlow = MutableStateFlow(
        EditLinkModel(
            instagram = initInstagram,
            x = initX,
            tiktok = initTiktok,
            youtube = initYoutube
        )
    )
    val editLinkModelStateFlow = _editLinkModelStateFlow.asStateFlow()

    fun setInstagram(instagram: String) {
        _editLinkModelStateFlow.update {
            it.copy(instagram = instagram)
        }
    }

    fun setX(x: String) {
        _editLinkModelStateFlow.update {
            it.copy(x = x)
        }
    }

    fun setTiktok(tiktok: String) {
        _editLinkModelStateFlow.update {
            it.copy(tiktok = tiktok)
        }
    }

    fun setYoutube(youtube: String) {
        _editLinkModelStateFlow.update {
            it.copy(youtube = youtube)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val instagram: String = "",
        private val x: String = "",
        private val tiktok: String = "",
        private val youtube: String = "",
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            EditLinkViewModel(
                initInstagram = instagram,
                initX = x,
                initTiktok = tiktok,
                initYoutube = youtube
            ) as T
    }
}