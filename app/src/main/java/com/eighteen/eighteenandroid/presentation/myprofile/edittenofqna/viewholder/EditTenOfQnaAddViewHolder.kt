package com.eighteen.eighteenandroid.presentation.myprofile.edittenofqna.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.eighteen.eighteenandroid.databinding.ItemEditTenOfQnaAddBinding

class EditTenOfQnaAddViewHolder(
    private val onClickAdd: () -> Unit,
    binding: ItemEditTenOfQnaAddBinding
) : ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener {
            onClickAdd.invoke()
        }
    }
}