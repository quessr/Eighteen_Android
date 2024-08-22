package com.eighteen.eighteenandroid.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.ItemAboutTeenBinding
import com.eighteen.eighteenandroid.domain.model.AboutTeen
import com.eighteen.eighteenandroid.presentation.home.adapter.diffcallback.AboutTeenDiffCallBack

class AboutTeenAdapter(
    private val listener: MainAdapterListener
): ListAdapter<AboutTeen, AboutTeenAdapter.AboutTeenViewHolder>(AboutTeenDiffCallBack()) {

    class AboutTeenViewHolder(
        private val binding: ItemAboutTeenBinding,
        private val listener: MainAdapterListener
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AboutTeen) {
            with(binding) {
                tvAboutTeenTitle.text = item.title
                tvAboutTeenDetail.text = item.description

                when(item.title) {
                    "Teen" -> imgAboutTeen.setImageResource(R.drawable.ic_about_teen)
                    "토너먼트", "채팅" -> imgAboutTeen.setImageResource(R.drawable.ic_chat)
                    "나만의 Teen" -> imgAboutTeen.setImageResource(R.drawable.ic_my_teen)
                }

                root.setOnClickListener {
                    listener.onAboutTeenClicks(item.title)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AboutTeenViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemAboutTeenBinding.inflate(layoutInflater, parent, false)
        return AboutTeenViewHolder(itemBinding, listener)
    }

    override fun onBindViewHolder(holder: AboutTeenViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}