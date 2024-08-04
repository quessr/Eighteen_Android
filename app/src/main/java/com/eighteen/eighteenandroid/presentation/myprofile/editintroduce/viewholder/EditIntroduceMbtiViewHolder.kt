package com.eighteen.eighteenandroid.presentation.myprofile.editintroduce.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.eighteen.eighteenandroid.databinding.ItemEditMbtiBinding
import com.eighteen.eighteenandroid.domain.model.Mbti
import com.eighteen.eighteenandroid.presentation.common.getDescriptionStringRes
import com.eighteen.eighteenandroid.presentation.myprofile.editintroduce.model.EditMbtiModel

class EditIntroduceMbtiViewHolder(
    private val binding: ItemEditMbtiBinding,
    private val onClick: (Mbti.MbtiType) -> Unit
) : ViewHolder(binding.root) {

    fun onBind(editMbtiModel: EditMbtiModel) {
        with(binding) {
            root.isSelected = editMbtiModel.isSelected
            tvMbti.text = editMbtiModel.mbtiType.alp.toString()
            val description =
                root.context.getString(editMbtiModel.mbtiType.getDescriptionStringRes())
            tvMbtiDescription.text = description
            root.setOnClickListener {
                onClick(editMbtiModel.mbtiType)
            }
        }
    }
}