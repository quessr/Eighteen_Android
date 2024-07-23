package com.eighteen.eighteenandroid.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.ItemAboutTeenBinding
import com.eighteen.eighteenandroid.domain.model.AboutTeen
import com.eighteen.eighteenandroid.presentation.home.adapter.diffcallback.AboutTeenDiffCallBack

class AboutTeenAdapter: ListAdapter<AboutTeen, AboutTeenAdapter.AboutTeenViewHolder>(AboutTeenDiffCallBack()) {

    inner class AboutTeenViewHolder(private val binding: ItemAboutTeenBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AboutTeen) {
            with(binding) {
                tvAboutTeenTitle.text = item.title
                tvAboutTeenDetail.text = item.description

                when(item.title) {
                    "Teen" -> imgAboutTeen.setImageResource(R.drawable.ic_about_teen)
                    "토너먼트", "채팅" -> imgAboutTeen.setImageResource(R.drawable.ic_chat)
                    "나만의 Teen" -> imgAboutTeen.setImageResource(R.drawable.ic_my_teen)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AboutTeenViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemAboutTeenBinding.inflate(layoutInflater, parent, false)
        return AboutTeenViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: AboutTeenViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}