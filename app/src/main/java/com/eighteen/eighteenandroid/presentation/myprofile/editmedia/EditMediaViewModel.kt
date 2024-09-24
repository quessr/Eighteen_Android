package com.eighteen.eighteenandroid.presentation.myprofile.editmedia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eighteen.eighteenandroid.presentation.myprofile.editmedia.model.EditMediaModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EditMediaViewModel(initModel: EditMediaModel) : ViewModel() {
    private val _mediasStateFlow = MutableStateFlow(initModel)
    val mediasStateFlow = _mediasStateFlow.asStateFlow()

    fun addMedia(model: EditMediaModel.Media) {
        _mediasStateFlow.update {
            val updatedMedias = it.medias.toMutableList().apply {
                add(model)
            }
            it.copy(medias = updatedMedias)
        }
    }

    fun addRefMedia(model: EditMediaModel.Media) {
        _mediasStateFlow.update {
            it.copy(repMedia = model)
        }
    }

    fun removeRefMedia() {
        _mediasStateFlow.update {
            it.copy(repMedia = null)
        }
    }

    fun removeMedia(idx: Int) {
        _mediasStateFlow.update {
            val updatedMedias = it.medias.toMutableList().apply {
                removeAt(idx)
            }
            it.copy(medias = updatedMedias)
        }
    }

    class Factory(private val initModel: EditMediaModel) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EditMediaViewModel(initModel = initModel) as T
        }
    }
}