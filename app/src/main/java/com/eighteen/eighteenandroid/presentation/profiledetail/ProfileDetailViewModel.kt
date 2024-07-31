package com.eighteen.eighteenandroid.presentation.profiledetail

import androidx.lifecycle.ViewModel
import com.eighteen.eighteenandroid.presentation.profiledetail.model.ProfileDetailModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileDetailViewModel : ViewModel() {
    private val _items = MutableStateFlow<List<ProfileDetailModel>>(emptyList())
    val items: StateFlow<List<ProfileDetailModel>> get() = _items

    private val _showAllItems = MutableStateFlow(false)
    val showAllItems: StateFlow<Boolean> get() = _showAllItems

    private val _qnaItems = MutableStateFlow<List<ProfileDetailModel.Qna>>(emptyList())
    val qnaItems: StateFlow<List<ProfileDetailModel.Qna>> get() = _qnaItems

    private val _currentPosition = MutableStateFlow(0)
    val currentPosition: StateFlow<Int> get() = _currentPosition


    companion object {
        const val ITEM_COUNT_THRESHOLD = 2
    }

    fun setItems(newItems: List<ProfileDetailModel>) {
        _items.value = newItems
    }

    fun setCurrentPosition(position: Int) {
        _currentPosition.value = position
    }

    fun restoreCurrentPosition(position: Int?) {
        _currentPosition.value = position ?: 0
    }

    fun toggleItems() {
        _showAllItems.value = !_showAllItems.value
        updateItems()
    }

    private fun updateItems() {
        val currentItems = _items.value.toMutableList()
        val qnaIndex = currentItems.indexOfFirst { it is ProfileDetailModel.QnaToggle }
        val qnaTitleIndex = currentItems.indexOfFirst { it is ProfileDetailModel.QnaListTitle }

        if (qnaIndex != -1) {
            val qnaList = _qnaItems.value
            val showAll = _showAllItems.value
            val qnaItemsToShow = if (showAll) qnaList else qnaList.take(ITEM_COUNT_THRESHOLD)

            currentItems.removeAll { it is ProfileDetailModel.Qna }

            // QnaListTitle 뒤에 Qna 항목들을 추가합니다.
            val insertIndex = qnaTitleIndex + 1
            if (insertIndex <= currentItems.size) {
                currentItems.addAll(insertIndex, qnaItemsToShow)
            } else {
                currentItems.addAll(qnaItemsToShow)
            }


            // 기존의 QnaToggle 항목이 있으면 제거합니다.
            val existingToggleIndex =
                currentItems.indexOfFirst { it is ProfileDetailModel.QnaToggle }
            if (existingToggleIndex != -1) {
                currentItems.removeAt(existingToggleIndex)
            }

            // Qna 항목들 뒤에 QnaToggle 항목을 추가합니다.
            if (qnaList.size > ITEM_COUNT_THRESHOLD) {
                val insertIndex = qnaIndex + 1 + qnaItemsToShow.size
                if (insertIndex <= currentItems.size) {
                    currentItems.add(
                        insertIndex,
                        ProfileDetailModel.QnaToggle(
                            id = "toggle",
                            isExpanded = _showAllItems.value
                        )
                    )
                } else {
                    currentItems.add(
                        ProfileDetailModel.QnaToggle(
                            id = "toggle",
                            isExpanded = _showAllItems.value
                        )
                    )
                }
            }

            _items.value = currentItems
        }
    }

    fun updateQnaToggle(updatedToggle: ProfileDetailModel.QnaToggle) {
        val currentItems = _items.value.toMutableList()
        val index = currentItems.indexOfFirst { it.id == updatedToggle.id }
        if (index != -1) {
            currentItems[index] = updatedToggle
            _items.value = currentItems
        }
    }

    fun updateQnaItems(qnaItems: List<ProfileDetailModel.Qna>) {
        _qnaItems.value = qnaItems
        updateItems()
    }
}