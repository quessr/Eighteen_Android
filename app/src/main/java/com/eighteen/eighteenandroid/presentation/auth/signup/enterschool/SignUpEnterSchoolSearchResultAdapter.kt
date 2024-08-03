package com.eighteen.eighteenandroid.presentation.auth.signup.enterschool

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.eighteen.eighteenandroid.databinding.ItemSignUpSchoolSearchResultBinding
import com.eighteen.eighteenandroid.domain.model.School
import com.eighteen.eighteenandroid.presentation.auth.signup.enterschool.viewholder.SignUpEnterSchoolSearchResultViewHolder

class SignUpEnterSchoolSearchResultAdapter(private val onClickSchool: (School) -> Unit) :
    ListAdapter<School, SignUpEnterSchoolSearchResultViewHolder>(diffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SignUpEnterSchoolSearchResultViewHolder {
        val binding =
            ItemSignUpSchoolSearchResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return SignUpEnterSchoolSearchResultViewHolder(
            binding = binding,
            onClickSchool = onClickSchool
        )
    }

    override fun onBindViewHolder(holder: SignUpEnterSchoolSearchResultViewHolder, position: Int) {
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