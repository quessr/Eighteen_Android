package com.eighteen.eighteenandroid.presentation.auth.signup.enterschool.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.eighteen.eighteenandroid.databinding.ItemSignUpSchoolSearchResultBinding
import com.eighteen.eighteenandroid.domain.model.School

class SignUpEnterSchoolSearchResultViewHolder(private val binding: ItemSignUpSchoolSearchResultBinding) :
    ViewHolder(binding.root) {

    fun onBind(schoolModel: School) {
        with(binding) {
            //TODO 클릭 리스너 추가
            tvSchoolName.text = schoolModel.name
            tvAddress.text = schoolModel.address
        }
    }
}