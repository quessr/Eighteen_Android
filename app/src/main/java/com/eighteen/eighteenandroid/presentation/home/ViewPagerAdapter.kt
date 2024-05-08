package com.eighteen.eighteenandroid.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eighteen.eighteenandroid.R

/**
 *
 * @file ViewPagerAdapter.kt
 * @date 05/08/2024
 */
class ViewPagerAdapter (
    private var tagList: List<String>,
) : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.today_teen_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = tagList[position]
        holder.title.text = item
    }

    override fun getItemCount(): Int {
        return tagList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tx_name)
    }
}