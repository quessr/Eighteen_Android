package com.eighteen.eighteenandroid.presentation.common.searchschool

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.eighteen.eighteenandroid.databinding.ItemSchoolSearchResultBinding
import com.eighteen.eighteenandroid.domain.model.School
import com.eighteen.eighteenandroid.presentation.common.searchschool.viewholder.SchoolSearchResultViewHolder

class SchoolSearchResultAdapter(private val onClickSchool: (School) -> Unit) :
    ListAdapter<School, SchoolSearchResultViewHolder>(diffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SchoolSearchResultViewHolder {
        val binding =
            ItemSchoolSearchResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return SchoolSearchResultViewHolder(
            binding = binding,
            onClickSchool = onClickSchool
        )
    }

    override fun onBindViewHolder(holder: SchoolSearchResultViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<School>() {
            override fun areItemsTheSame(oldItem: School, newItem: School): Boolean = true

            override fun areContentsTheSame(oldItem: School, newItem: School): Boolean =
                oldItem == newItem
        }
    }
}