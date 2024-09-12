package com.eighteen.eighteenandroid.presentation.teen

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.ItemTeenTypeBinding
import com.eighteen.eighteenandroid.domain.model.TeenType

class TeenTypeAdapter(
    private val context: Context
): ListAdapter<TeenType, TeenTypeAdapter.TeenTypeViewHolder>(diffUtil) {

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<TeenType>() {
            override fun areItemsTheSame(oldItem: TeenType, newItem: TeenType): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: TeenType, newItem: TeenType): Boolean =
                oldItem == newItem

        }
    }

    class TeenTypeViewHolder(val context: Context, val binding: ItemTeenTypeBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: TeenType) {
            with(binding) {
                when(item) {
                    TeenType.MY_CHOICE_TEEN -> {
                        tvTeenTypeTitle.text = context.getString(R.string.text_teen_my_choice)
                        ivTeenType.setImageResource(R.drawable.ic_teen_my_choice)
                    }

                    TeenType.AWARDS_TEEN -> {
                        tvTeenTypeTitle.text = context.getString(R.string.text_teen_awards)
                        ivTeenType.setImageResource(R.drawable.ic_teen_awards)
                    }

                    TeenType.MOST_BADGE_TEEN -> {
                        tvTeenTypeTitle.text = context.getString(R.string.text_teen_most_badge)
                        ivTeenType.setImageResource(R.drawable.ic_teen_most_badge)
                    }

                    TeenType.MOST_VOTE_COUNT_TEEN -> {
                        tvTeenTypeTitle.text = context.getString(R.string.text_teen_most_vote)
                        ivTeenType.setImageResource(R.drawable.ic_teen_most_vote)
                    }

                    TeenType.MONTHLY_POPULAR_TEEN -> {
                        tvTeenTypeTitle.text = context.getString(R.string.text_teen_monthly)
                        ivTeenType.setImageResource(R.drawable.ic_teen_monthly)
                    }

                    TeenType.RECENT_JOIN_TEEN -> {
                        tvTeenTypeTitle.text = context.getString(R.string.text_teen_recent_join)
                        ivTeenType.setImageResource(R.drawable.ic_teen_recent_join)
                    }

                    TeenType.SAME_SCHOOL_TEEN -> {
                        tvTeenTypeTitle.text = context.getString(R.string.text_teen_same_school)
                        ivTeenType.setImageResource(R.drawable.ic_teen_same_school)
                    }

                    TeenType.WEEKLY_POPULAR_TEEN -> {
                        tvTeenTypeTitle.text = context.getString(R.string.text_teen_weekly)
                        ivTeenType.setImageResource(R.drawable.ic_teen_weekly)
                    }
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeenTypeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTeenTypeBinding.inflate(inflater, parent, false)

        return TeenTypeViewHolder(context, binding)
    }

    override fun onBindViewHolder(holder: TeenTypeViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}


