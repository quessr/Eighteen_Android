package com.eighteen.eighteenandroid.presentation.myprofile.edittenofqna

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eighteen.eighteenandroid.domain.model.Qna
import com.eighteen.eighteenandroid.domain.model.QnaType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EditTenOfQnaViewModel(initQnas: List<Qna>) : ViewModel() {

    private val _editTenOfQnaModelsStateFlow = MutableStateFlow(initQnas)
    val editTenOfQnaModelsStateFlow = _editTenOfQnaModelsStateFlow.asStateFlow()

    fun removeQna(qnaType: QnaType) {
        _editTenOfQnaModelsStateFlow.update {
            it.toMutableList().apply {
                removeIf { it.question == qnaType }
            }
        }
    }

    fun addQna(qnaType: QnaType, answer: String) {
        _editTenOfQnaModelsStateFlow.update {
            it.toMutableList().apply {
                add(Qna(question = qnaType, answer = answer))
            }
        }
    }

    class Factory(
        private val initQnas: List<Qna>
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EditTenOfQnaViewModel(initQnas) as T
        }
    }
}