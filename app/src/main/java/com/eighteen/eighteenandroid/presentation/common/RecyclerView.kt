package com.eighteen.eighteenandroid.presentation.common

import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView

/**
 * Get main RV child view holder
 * @param T: ViewHolder 타입
 * @return 현재 RecyclerView에서 찾을 경우 ViewHolder class or 못 찾을 경우 null
 */
inline fun <reified T : RecyclerView.ViewHolder> RecyclerView.findViewHolderOrNull(): T? {
    return children.map {
        val adapterPosition = getChildAdapterPosition(it)
        findViewHolderForAdapterPosition(adapterPosition)
    }.filterIsInstance<T>().firstOrNull()
}