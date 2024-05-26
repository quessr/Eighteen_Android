package com.eighteen.eighteenandroid.presentation.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.domain.model.User

/**
 *
 * @file ViewPagerAdapter.kt
 * @date 05/08/2024
 */
class TodayTeenAdapter(
    private val context: Context,
    private var userList: List<User> = mutableListOf(),
) : RecyclerView.Adapter<TodayTeenAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.today_teen_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        Glide.with(context).load(user.userImage).into(holder.todayTeenImage);
        holder.todayTeenName.text = user.userName
        holder.todayTeenAge.text = user.userAge
        holder.todayTeenSchool.text = user.userSchoolName

        // fragmentMain에서 fragmentProfileDetail로 네비게이션 진행
        initNavigation(holder.todayTeenImage)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val todayTeenImage: ImageView = view.findViewById(R.id.img_today_teen)
        val todayTeenName: TextView = view.findViewById(R.id.tx_name)
        val todayTeenAge: TextView = view.findViewById(R.id.tx_age)
        val todayTeenSchool: TextView = view.findViewById(R.id.tx_school)
    }

    private fun initNavigation(profileImageView: ImageView) {
        profileImageView.setOnClickListener { view ->
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_fragmentMain_to_fragmentProfileDetail)
        }
    }

    fun updateData(newTeenList: List<User>) {
        userList = newTeenList
        notifyDataSetChanged()
    }
}