package com.eighteen.eighteenandroid.presentation.auth.signup.selecttag.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.eighteen.eighteenandroid.common.enums.Tag
import com.eighteen.eighteenandroid.databinding.ItemSignUpTagBinding
import com.eighteen.eighteenandroid.presentation.auth.signup.selecttag.model.SignUpTagModel

class SignUpTagViewHolder(
    private val binding: ItemSignUpTagBinding,
    private val onClick: (Tag) -> Unit
) : ViewHolder(binding.root) {
    fun onBind(model: SignUpTagModel) {
        with(binding) {
            tvTag.text = model.tag.strValue
            root.setOnClickListener {
                onClick.invoke(model.tag)
            }
            root.isSelected = model.isSelected
        }

    }
}