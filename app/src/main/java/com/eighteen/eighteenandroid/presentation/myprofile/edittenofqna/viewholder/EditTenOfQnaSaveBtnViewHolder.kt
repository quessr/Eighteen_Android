package com.eighteen.eighteenandroid.presentation.myprofile.edittenofqna.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.eighteen.eighteenandroid.databinding.ItemEditTenOfQnaBtnSaveBinding
import com.eighteen.eighteenandroid.presentation.myprofile.edittenofqna.model.EditTenOfQnaModel

class EditTenOfQnaSaveBtnViewHolder(
    private val binding: ItemEditTenOfQnaBtnSaveBinding,
    private val onClick: () -> Unit
) : ViewHolder(binding.root) {

    init {
        binding.tvBtnSave.setOnClickListener { onClick() }
    }

    fun onBind(model: EditTenOfQnaModel.SaveBtn) {
        binding.tvBtnSave.isEnabled = model.isEnabled
    }
}