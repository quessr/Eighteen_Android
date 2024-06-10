package com.eighteen.eighteenandroid.presentation.profiledetail

import android.view.View
import android.view.ViewGroup
import android.widget.RemoteViews.RemoteCollectionItems
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileDetailViewModel : ViewModel() {
    private val _items = MutableStateFlow<List<Pair<String, String>>>(emptyList())
    val items: StateFlow<List<Pair<String, String>>> get() = _items

    private val _showAllItems = MutableStateFlow(false)
    val showAllItems: StateFlow<Boolean> get() = _showAllItems

    companion object {
        const val ITEM_COUNT_THRESHOLD = 2
    }

    fun setItems(newItems: List<Pair<String, String>>) {
        _items.value = newItems
    }

    fun toggleItems() {
        _showAllItems.value = !_showAllItems.value
    }

    fun getItemCount(): Int {
        val itemList = _items.value
        return if (_showAllItems.value || itemList.isEmpty() || itemList.size <= ITEM_COUNT_THRESHOLD) {
            itemList.size
        } else {
            ITEM_COUNT_THRESHOLD
        }
    }


}