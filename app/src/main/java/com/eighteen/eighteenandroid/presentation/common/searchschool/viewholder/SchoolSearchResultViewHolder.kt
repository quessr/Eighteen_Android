package com.eighteen.eighteenandroid.presentation.common.searchschool.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.eighteen.eighteenandroid.databinding.ItemSchoolSearchResultBinding
import com.eighteen.eighteenandroid.domain.model.School

class SchoolSearchResultViewHolder(
    private val binding: ItemSchoolSearchResultBinding,
    private val onClickSchool: (School) -> Unit
) : ViewHolder(binding.root) {

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