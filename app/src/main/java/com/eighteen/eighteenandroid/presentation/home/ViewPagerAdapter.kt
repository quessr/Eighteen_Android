package com.eighteen.eighteenandroid.presentation.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eighteen.eighteenandroid.R

/**
 *
 * @file ViewPagerAdapter.kt
 * @date 05/08/2024
 */
class ViewPagerAdapter(
    private val context: Context,
    private var tagList: List<String>,
) : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.today_teen_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = tagList[position]
        Glide.with(context).load(item).into(holder.profileImg);

        // fragmentMain에서 fragmentProfileDetail로 네비게이션 진행
        initNavigation(holder.profileImg)
    }

    override fun getItemCount(): Int {
        return tagList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val profileImg: ImageView = view.findViewById(R.id.img_today_teen)
    }

    private fun initNavigation(profileImageView: ImageView) {
        profileImageView.setOnClickListener { view ->
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_fragmentMain_to_fragmentProfileDetail)
        }
    }
}