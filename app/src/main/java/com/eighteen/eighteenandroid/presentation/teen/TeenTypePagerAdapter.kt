package com.eighteen.eighteenandroid.presentation.teen

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eighteen.eighteenandroid.databinding.ItemTeenTypePagerBinding
import com.eighteen.eighteenandroid.domain.model.TeenType

class TeenTypePagerAdapter(
    private val context: Context,
    private val items: List<TeenType>
): RecyclerView.Adapter<TeenTypePagerAdapter.PagerViewHolder>() {

    class PagerViewHolder(private val context: Context, private val binding: ItemTeenTypePagerBinding) : RecyclerView.ViewHolder(binding.root) {
        val adapter = TeenTypeAdapter(context)

        fun onBind(pageItems: List<TeenType>) {
            with(binding) {
                rvTeenRankingPager.adapter = adapter
                rvTeenRankingPager.layoutManager = object: GridLayoutManager(context, 2) {
                    override fun canScrollVertically(): Boolean = false
                }

                adapter.submitList(pageItems)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTeenTypePagerBinding.inflate(inflater, parent, false)

        return PagerViewHolder(context, binding)
    }

    override fun getItemCount(): Int = (items.size + 3) / 4

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val startIndex = position * 4
        val pageItems = items.subList(startIndex, minOf(startIndex + 4, items.size))

        holder.onBind(pageItems)
    }
}