package com.eighteen.eighteenandroid.presentation.myprofile.edittenofqna

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.eighteen.eighteenandroid.databinding.ItemEditTenOfQnaAddBinding
import com.eighteen.eighteenandroid.databinding.ItemEditTenOfQnaInputBinding
import com.eighteen.eighteenandroid.databinding.ItemEditTenOfQnaTitleBinding
import com.eighteen.eighteenandroid.domain.model.QnaType
import com.eighteen.eighteenandroid.presentation.myprofile.edittenofqna.model.EditTenOfQnaModel
import com.eighteen.eighteenandroid.presentation.myprofile.edittenofqna.viewholder.EditTenOfQnaAddViewHolder
import com.eighteen.eighteenandroid.presentation.myprofile.edittenofqna.viewholder.EditTenOfQnaInputViewHolder
import com.eighteen.eighteenandroid.presentation.myprofile.edittenofqna.viewholder.EditTenOfQnaTitleViewHolder

class EditTenOfQnaAdapter(
    private val getInput: (QnaType) -> String,
    private val setInput: (QnaType, String) -> Unit,
    private val onClickRemove: (QnaType) -> Unit,
    private val onClickAdd: () -> Unit,
) : ListAdapter<EditTenOfQnaModel, ViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM_TYPE_TITLE -> {
                val binding = ItemEditTenOfQnaTitleBinding.inflate(layoutInflater, parent, false)
                EditTenOfQnaTitleViewHolder(binding = binding)
            }
            ITEM_TYPE_INPUT -> {
                val binding = ItemEditTenOfQnaInputBinding.inflate(layoutInflater, parent, false)
                EditTenOfQnaInputViewHolder(
                    binding = binding,
                    getInput = getInput,
                    setInput = setInput,
                    onClickRemove = onClickRemove
                )
            }
            else -> {
                val binding = ItemEditTenOfQnaAddBinding.inflate(layoutInflater, parent, false)
                EditTenOfQnaAddViewHolder(binding = binding, onClickAdd = onClickAdd)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (val model = getItem(position)) {
            is EditTenOfQnaModel.Input -> (holder as? EditTenOfQnaInputViewHolder)?.onBind(model = model)
            else -> {
                //do nothing
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is EditTenOfQnaModel.Title -> ITEM_TYPE_TITLE
            is EditTenOfQnaModel.Input -> ITEM_TYPE_INPUT
            is EditTenOfQnaModel.Add -> ITEM_TYPE_ADD
        }
    }

    companion object {
        private const val ITEM_TYPE_TITLE = 1
        private const val ITEM_TYPE_INPUT = 2
        private const val ITEM_TYPE_ADD = 3
        private val diffUtil = object : DiffUtil.ItemCallback<EditTenOfQnaModel>() {
            override fun areItemsTheSame(
                oldItem: EditTenOfQnaModel,
                newItem: EditTenOfQnaModel
            ): Boolean = oldItem.areItemsTheSame(newItem)

            override fun areContentsTheSame(
                oldItem: EditTenOfQnaModel,
                newItem: EditTenOfQnaModel
            ): Boolean = oldItem.areContentsTheSame(newItem)
        }
    }
}