package com.eighteen.eighteenandroid.presentation.myprofile.editlink

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.eighteen.eighteenandroid.domain.model.SnsInfo
import com.eighteen.eighteenandroid.domain.usecase.EditSnsLinkUseCase
import com.eighteen.eighteenandroid.presentation.common.ModelState
import com.eighteen.eighteenandroid.presentation.common.livedata.Event
import com.eighteen.eighteenandroid.presentation.myprofile.editlink.model.EditLinkModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditLinkViewModel @AssistedInject constructor(
    private val editSnsLinkUseCase: EditSnsLinkUseCase,
    @Assisted("instagram") private val initInstagram: String,
    @Assisted("x") private val initX: String,
    @Assisted("tiktok") private val initTiktok: String,
    @Assisted("youtube") private val initYoutube: String
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

    private val _editLinkResultEventStateFlow =
        MutableStateFlow<ModelState<Event<List<SnsInfo>>>>(ModelState.Empty())
    val editLinkResultStateFlow = _editLinkResultEventStateFlow.asStateFlow()

    private var editLinkJob: Job? = null

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

    fun requestEditLinks() {
        if (editLinkJob?.isCompleted == false) return
        editLinkJob = viewModelScope.launch {
            val snsInfoList = editLinkModelStateFlow.value.toSnsInfoList()
            editSnsLinkUseCase.invoke(snsInfoList = snsInfoList).onSuccess {
                _editLinkResultEventStateFlow.value = ModelState.Success(Event(snsInfoList))
            }.onFailure {
                _editLinkResultEventStateFlow.value = ModelState.Error(throwable = it)
            }
        }
    }

    private fun EditLinkModel.toSnsInfoList(): List<SnsInfo> {
        val instagramSnsInfo = SnsInfo(type = SnsInfo.SnsType.INSTAGRAM, id = this.instagram)
        val xSnsInfo = SnsInfo(type = SnsInfo.SnsType.X, id = this.x)
        val tiktokSnsInfo = SnsInfo(type = SnsInfo.SnsType.TIKTOK, id = this.tiktok)
        val youtubeSnsInfo = SnsInfo(type = SnsInfo.SnsType.YOUTUBE, id = this.youtube)
        return listOf(
            instagramSnsInfo,
            xSnsInfo,
            tiktokSnsInfo,
            youtubeSnsInfo
        ).filter { it.id.isNotEmpty() }
    }

    @AssistedFactory
    interface EditLinkViewModelAssistedFactory {
        fun create(
            @Assisted("instagram") initInstagram: String = "",
            @Assisted("x") initX: String = "",
            @Assisted("tiktok") initTiktok: String = "",
            @Assisted("youtube") initYoutube: String = ""
        ): EditLinkViewModel
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val instagram: String = "",
        private val x: String = "",
        private val tiktok: String = "",
        private val youtube: String = "",
        private val assistedFactory: EditLinkViewModelAssistedFactory
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            assistedFactory.create(
                initInstagram = instagram,
                initX = x,
                initTiktok = tiktok,
                initYoutube = youtube
            ) as T
    }
}