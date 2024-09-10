package com.eighteen.eighteenandroid.presentation.dialog.selectqna

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eighteen.eighteenandroid.databinding.ItemSelectQnaDialogQnaBinding
import com.eighteen.eighteenandroid.domain.model.QnaType
import com.eighteen.eighteenandroid.presentation.dialog.selectqna.model.SelectQnaDialogModel
import com.eighteen.eighteenandroid.presentation.dialog.selectqna.viewholder.SelectQnaDialogQnaViewHolder

class SelectQnaDialogAdapter(private val onClickItem: (QnaType) -> Unit) :
    ListAdapter<SelectQnaDialogModel, RecyclerView.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemSelectQnaDialogQnaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SelectQnaDialogQnaViewHolder(binding = binding, onClick = onClickItem)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? SelectQnaDialogQnaViewHolder)?.onBind(
            model = getItem(position),
            idx = position
        )
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<SelectQnaDialogModel>() {
            override fun areItemsTheSame(
                oldItem: SelectQnaDialogModel,
                newItem: SelectQnaDialogModel
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: SelectQnaDialogModel,
                newItem: SelectQnaDialogModel
            ): Boolean = oldItem == newItem
        }
    }
}