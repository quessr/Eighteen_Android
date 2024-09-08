package com.eighteen.eighteenandroid.presentation.myprofile.edittenofqna

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eighteen.eighteenandroid.domain.model.Qna
import com.eighteen.eighteenandroid.presentation.common.ModelState
import com.eighteen.eighteenandroid.presentation.common.livedata.Event
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class EditTenOfQnaViewModel @AssistedInject constructor(@Assisted private val initQnas: List<Qna>) :
    ViewModel() {

    private val _requestEditQnaResultStateFlow =
        MutableStateFlow<ModelState<Event<List<Qna>>>>(ModelState.Empty())
    val requestEditQnaResultStateFlow = _requestEditQnaResultStateFlow.asStateFlow()

    private val _editTenOfQnaModelsStateFlow = MutableStateFlow(initQnas)
    val editTenOfQnaModelsStateFlow = _editTenOfQnaModelsStateFlow.asStateFlow()

    private var editTenOfQnaJob: Job? = null


    @AssistedFactory
    interface EditTenOfQnaAssistedFactory {
        fun create(initQnas: List<Qna>): EditTenOfQnaViewModel
    }

    class Factory(
        private val assistedFactory: EditTenOfQnaAssistedFactory,
        private val initQnas: List<Qna>
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return assistedFactory.create(initQnas) as T
        }
    }
}