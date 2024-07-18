package com.eighteen.eighteenandroid.presentation.auth.signup.enterschool.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.eighteen.eighteenandroid.databinding.ItemSignUpSchoolSearchResultBinding
import com.eighteen.eighteenandroid.domain.model.School

class SignUpEnterSchoolSearchResultViewHolder(
    private val binding: ItemSignUpSchoolSearchResultBinding,
    private val onClickSchool: (School) -> Unit
) :
    ViewHolder(binding.root) {

    fun onBind(schoolModel: School) {
        with(binding) {
            root.setOnClickListener {
                onClickSchool.invoke(schoolModel)
            }
            tvSchoolName.text = schoolModel.name
            tvAddress.text = schoolModel.address
        }
    }
}