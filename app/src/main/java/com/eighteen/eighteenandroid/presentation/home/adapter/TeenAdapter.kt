package com.eighteen.eighteenandroid.presentation.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.ItemPopularTeenBinding
import com.eighteen.eighteenandroid.domain.model.User
import com.eighteen.eighteenandroid.presentation.home.adapter.diffcallback.UserDiffCallBack

/**
 *
 * @file TeenAdapter.kt
 * @date 05/08/2024
 * 오늘의 Teen & 또다른 Teen 목록에 대한 RecyclerView Adapter
 */
class TeenAdapter(
    private val context: Context,
    private val showUserReportSelectDialog:(User) -> Unit
) : ListAdapter<User, TeenAdapter.TeenViewHolder>(UserDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeenViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemPopularTeenBinding.inflate(layoutInflater, parent,false)

        return TeenViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: TeenViewHolder, position: Int) {
        holder.bind(getItem(position), context)
    }

    inner class TeenViewHolder(
        private val binding: ItemPopularTeenBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: User, context: Context) {
            binding.run {
                Glide.with(context).load(item.userImage).into(imgTodayTeen)
                tvName.text = "${item.userName}, ${item.userAge}"
                tvSchool.text = item.userSchoolName
                initNavigation(binding.imgTodayTeen)
                btnSetting.setOnClickListener {
                    showUserReportSelectDialog(item)
                }
            }
        }

        /** 유저 상세로 이동 Navigation */
        private fun initNavigation(profileImageView: ImageView) {
            profileImageView.setOnClickListener { view ->
                val navController = Navigation.findNavController(view)
                navController.navigate(R.id.action_fragmentMain_to_fragmentProfileDetail)
            }
        }

    }
}