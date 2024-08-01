package com.eighteen.eighteenandroid.presentation.myprofile.edittenofqna

import androidx.lifecycle.ViewModel
import com.eighteen.eighteenandroid.domain.model.QnaType
import com.eighteen.eighteenandroid.presentation.myprofile.edittenofqna.model.EditTenOfQnaModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EditTenOfQnaViewModel @Inject constructor() : ViewModel() {
    private val qnaAnswerInputMap = mutableMapOf<QnaType, String>()
    private val _editTenOfQnaModelStateFlow = MutableStateFlow(
        listOf(
            EditTenOfQnaModel.Title,
            EditTenOfQnaModel.Add
        )
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
            addAll(inputs)
            if (inputs.size < MAXIMUM_QNA_COUNT) add(EditTenOfQnaModel.Add)
        }

    companion object {
        private const val MAXIMUM_QNA_COUNT = 10
    }
}