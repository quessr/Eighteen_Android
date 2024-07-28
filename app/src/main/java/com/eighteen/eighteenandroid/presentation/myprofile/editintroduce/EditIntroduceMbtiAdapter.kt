package com.eighteen.eighteenandroid.presentation.myprofile.editintroduce

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.eighteen.eighteenandroid.databinding.ItemEditMbtiBinding
import com.eighteen.eighteenandroid.domain.model.Mbti
import com.eighteen.eighteenandroid.presentation.myprofile.editintroduce.model.EditMbtiModel
import com.eighteen.eighteenandroid.presentation.myprofile.editintroduce.viewholder.EditIntroduceMbtiViewHolder

class EditIntroduceMbtiAdapter(private val onClickItem: (Mbti) -> Unit) :
    ListAdapter<EditMbtiModel, EditIntroduceMbtiViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditIntroduceMbtiViewHolder {
        val binding =
            ItemEditMbtiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EditIntroduceMbtiViewHolder(binding = binding, onClick = onClickItem)
    }

    override fun onBindViewHolder(holder: EditIntroduceMbtiViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<EditMbtiModel>() {
            override fun areItemsTheSame(oldItem: EditMbtiModel, newItem: EditMbtiModel): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: EditMbtiModel,
                newItem: EditMbtiModel
            ): Boolean = oldItem == newItem
        }
    }
}