package com.eighteen.eighteenandroid.presentation.myprofile.edittenofqna

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eighteen.eighteenandroid.domain.model.Qna
import com.eighteen.eighteenandroid.domain.model.QnaType
import com.eighteen.eighteenandroid.presentation.myprofile.edittenofqna.model.EditTenOfQnaModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

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
    val editTenOfQnaModelStateFlow = _editTenOfQnaModelStateFlow.asStateFlow()

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
        private const val MAXIMUM_QNA_COUNT = 10
    }
}