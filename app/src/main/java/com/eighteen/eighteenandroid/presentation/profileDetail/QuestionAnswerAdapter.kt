package com.eighteen.eighteenandroid.presentation.profileDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eighteen.eighteenandroid.databinding.QuestionAnswerItemBinding
import com.eighteen.eighteenandroid.databinding.QuestionAnswerItemWithSeeMoreBinding

class QuestionAnswerAdapter(private val items: List<Pair<String, String>>, private val recyclerView: RecyclerView) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ITEM_TYPE_NORMAL = 0
        private const val ITEM_TYPE_LAST = 1
        private const val ITEM_COUNT_THRESHOLD = 2
    }

    private var showAllItems: Boolean = false
    private fun showAllItems() {
        showAllItems = true
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (!showAllItems && items.size > ITEM_COUNT_THRESHOLD && position == ITEM_COUNT_THRESHOLD) ITEM_TYPE_LAST else ITEM_TYPE_NORMAL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            ITEM_TYPE_LAST -> {
                val binding = QuestionAnswerItemWithSeeMoreBinding.inflate(inflater, parent, false)
                LastViewHolder(binding) { showAllItems() }
            }

            else -> {
                val binding = QuestionAnswerItemBinding.inflate(inflater, parent, false)
                NormalViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val item = items[position]
//        when (holder) {
//            is NormalViewHolder -> {
//                holder.bind(item.first, item.second)
//            }
//
//            is LastViewHolder -> {
//                holder.bind(item.first, item.second)
//            }
//        }
        if (getItemViewType(position) == ITEM_TYPE_LAST) {
            val item = items[position - 1]
            (holder as LastViewHolder).bind(item.first, item.second)
        } else {
            val item = items[position]
            (holder as NormalViewHolder).bind(item.first, item.second)
        }
    }

    override fun getItemCount(): Int {
        return if (showAllItems || items.size <= ITEM_COUNT_THRESHOLD) items.size else ITEM_COUNT_THRESHOLD + 1
    }

    class NormalViewHolder(private val binding: QuestionAnswerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(question: String, answer: String) {
            binding.question.text = question
            binding.answer.text = answer
        }
    }

    class LastViewHolder(private val binding: QuestionAnswerItemWithSeeMoreBinding,   private val onClick: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(question: String, answer: String) {
            binding.question.text = question
            binding.answer.text = answer

            binding.tvSeeMore.setOnClickListener {
                onClick()
            }
        }
    }
}