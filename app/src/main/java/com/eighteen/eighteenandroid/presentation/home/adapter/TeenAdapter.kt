package com.eighteen.eighteenandroid.presentation.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.TodayTeenItemBinding
import com.eighteen.eighteenandroid.domain.model.User

/**
 *
 * @file TeenAdapter.kt
 * @date 05/08/2024
 * 오늘의 Teen & 또다른 Teen 목록에 대한 RecyclerView Adapter
 */
class TeenAdapter(
    private val context: Context,
    private val showDialog:() -> Unit
) : RecyclerView.Adapter<TeenAdapter.ViewHolder>() {

    private var userList: MutableList<User> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TodayTeenItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding = binding, showDialog = showDialog)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user, context)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    // 또 다른 틴 스크롤
    class ViewHolder(
        private val binding: TodayTeenItemBinding,
        private val showDialog: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: User, context: Context) {
            binding.run {
                Glide.with(context).load(item.userImage).into(imgTodayTeen)
                txName.text = item.userName
                txAge.text = item.userAge
                txSchool.text = item.userSchoolName
                initNavigation(binding.imgTodayTeen)
                btnSetting.setOnClickListener {
                    showDialog()
                }
            }
        }

        // fragmentMain에서 fragmentProfileDetail로 네비게이션 진행
        private fun initNavigation(profileImageView: ImageView) {
            profileImageView.setOnClickListener { view ->
                val navController = Navigation.findNavController(view)
                navController.navigate(R.id.action_fragmentMain_to_fragmentProfileDetail)
            }
        }

    }

    fun updateView(newList: List<User>) {
        val diffUtil = DiffUtilCallback(userList, newList)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffUtil)
        userList.clear()
        userList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }
}