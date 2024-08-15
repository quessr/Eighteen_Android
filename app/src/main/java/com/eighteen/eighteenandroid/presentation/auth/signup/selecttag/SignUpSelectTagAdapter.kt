package com.eighteen.eighteenandroid.presentation.auth.signup.selecttag

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.eighteen.eighteenandroid.common.enums.Tag
import com.eighteen.eighteenandroid.databinding.ItemSignUpTagBinding
import com.eighteen.eighteenandroid.presentation.auth.signup.selecttag.model.SignUpTagModel
import com.eighteen.eighteenandroid.presentation.auth.signup.selecttag.viewholder.SignUpTagViewHolder

class SignUpSelectTagAdapter(private val onClick: (Tag) -> Unit) :
    ListAdapter<SignUpTagModel, SignUpTagViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SignUpTagViewHolder {
        val binding =
            ItemSignUpTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SignUpTagViewHolder(onClick = onClick, binding = binding)
    }

    override fun onBindViewHolder(holder: SignUpTagViewHolder, position: Int) {
        holder.onBind(model = getItem(position))
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<SignUpTagModel>() {
            override fun areItemsTheSame(
                oldItem: SignUpTagModel,
                newItem: SignUpTagModel
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: SignUpTagModel,
                newItem: SignUpTagModel
            ): Boolean =
                oldItem == newItem
        }
    }
}