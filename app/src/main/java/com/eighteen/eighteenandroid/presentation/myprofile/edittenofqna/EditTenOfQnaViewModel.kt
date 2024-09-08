package com.eighteen.eighteenandroid.presentation.myprofile.edittenofqna

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.eighteen.eighteenandroid.domain.model.Qna
import com.eighteen.eighteenandroid.domain.model.QnaType
import com.eighteen.eighteenandroid.presentation.common.ModelState
import com.eighteen.eighteenandroid.presentation.common.livedata.Event
import com.eighteen.eighteenandroid.presentation.myprofile.edittenofqna.model.EditTenOfQnaModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditTenOfQnaViewModel @AssistedInject constructor(@Assisted private val initQnas: List<Qna>) :
    ViewModel() {
    private val qnaAnswerInputMap = mutableMapOf<QnaType, String>()
    private val _editTenOfQnaModelStateFlow = MutableStateFlow(
        createUiModels(inputs = initQnas.mapIndexed { idx, qna ->
            setInput(qna.question, qna.answer)
            EditTenOfQnaModel.Input(
                qna = qna.question,
                position = idx + 1
            )
        })
    )

    private val _requestEditQnaResultStateFlow =
        MutableStateFlow<ModelState<Event<List<Qna>>>>(ModelState.Empty())
    val requestEditQnaResultStateFlow = _requestEditQnaResultStateFlow.asStateFlow()

    val editTenOfQnaModelStateFlow = _editTenOfQnaModelStateFlow.asStateFlow()

    private var editTenOfQnaJob: Job? = null

    val inputModels get() = _editTenOfQnaModelStateFlow.value.filterIsInstance<EditTenOfQnaModel.Input>()
    fun setInput(qnaType: QnaType, input: String) {
        qnaAnswerInputMap[qnaType] = input
    }

    fun getInput(qnaType: QnaType) = qnaAnswerInputMap.getOrDefault(qnaType, "")

    fun removeQna(qnaType: QnaType) {
        qnaAnswerInputMap.remove(qnaType)
        val updatedInputList =
            inputModels.toMutableList().apply {
                removeIf {
                    (it as? EditTenOfQnaModel.Input)?.qna == qnaType
                }
            }
        _editTenOfQnaModelStateFlow.value = createUiModels(inputs = updatedInputList)
    }

    fun addQna(qnaType: QnaType) {
        val updatedInputList =
            inputModels.toMutableList().apply {
                add(EditTenOfQnaModel.Input(qna = qnaType, position = size + 1))
            }
        _editTenOfQnaModelStateFlow.value = createUiModels(inputs = updatedInputList)
    }

    private fun createUiModels(inputs: List<EditTenOfQnaModel.Input>) =
        mutableListOf<EditTenOfQnaModel>().apply {
            add(EditTenOfQnaModel.Title)
            addAll(inputs.mapIndexed { idx, input -> input.copy(position = idx + 1) })
            if (inputs.size < MAXIMUM_QNA_COUNT) add(EditTenOfQnaModel.Add)
            add(EditTenOfQnaModel.SaveBtn(isEnabled = inputs.size >= MINIMUM_QNA_COUNT))
        }

    fun requestEditQnas() {
        if (editTenOfQnaJob?.isCompleted == false) return
        editTenOfQnaJob = viewModelScope.launch {
            val qnas =
                inputModels.map { Qna(question = it.qna, answer = qnaAnswerInputMap[it.qna] ?: "") }
            //TODO usecase 구현
            _requestEditQnaResultStateFlow.value = ModelState.Success(Event(qnas))
        }
    }

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


    companion object {
        private const val MINIMUM_QNA_COUNT = 3
        private const val MAXIMUM_QNA_COUNT = 10
    }
}