package com.eighteen.eighteenandroid.presentation.myprofile.editmedia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eighteen.eighteenandroid.presentation.common.livedata.Event
import com.eighteen.eighteenandroid.presentation.myprofile.editmedia.model.MyEditMediaEvent
import com.eighteen.eighteenandroid.presentation.myprofile.editmedia.model.MyEditMediaModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MyEditMediaViewModel(initModel: MyEditMediaModel) : ViewModel() {
    private val _mediasStateFlow = MutableStateFlow(initModel)
    val mediasStateFlow = _mediasStateFlow.asStateFlow()

    private val _openEditMediaEventLiveData = MutableLiveData<Event<MyEditMediaEvent>>()
    val openEditMediaEventLiveData: LiveData<Event<MyEditMediaEvent>> = _openEditMediaEventLiveData

    fun addMedia(model: MyEditMediaModel.Media) {
        _mediasStateFlow.update {
            val updatedMedias = it.medias.toMutableList().apply {
                add(model)
            }
            it.copy(medias = updatedMedias)
        }
    }

    fun addRepMedia(model: MyEditMediaModel.Media) {
        _mediasStateFlow.update {
            it.copy(repMedia = model)
        }
    }

    fun removeRepMedia() {
        _mediasStateFlow.update {
            it.copy(repMedia = null)
        }
    }

    fun removeMedia(model: MyEditMediaModel.Media) {
        _mediasStateFlow.update {
            val updatedMedias = it.medias.toMutableList().apply {
                removeIf { mediaModel -> model == mediaModel }
            }
            it.copy(medias = updatedMedias)
        }
    }

    fun setOpenEditMediaEvent(event: MyEditMediaEvent) {
        _openEditMediaEventLiveData.value = Event(event)
    }

    class Factory(private val initModel: MyEditMediaModel) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MyEditMediaViewModel(initModel = initModel) as T
        }
    }
}