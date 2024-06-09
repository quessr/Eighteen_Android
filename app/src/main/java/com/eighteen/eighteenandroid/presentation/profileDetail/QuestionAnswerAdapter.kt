package com.eighteen.eighteenandroid.presentation.profileDetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eighteen.eighteenandroid.databinding.QuestionAnswerItemBinding

class QuestionAnswerAdapter(
    private val items: List<Pair<String, String>>,
) :
    RecyclerView.Adapter<QuestionAnswerAdapter.ViewHolder>() {

    companion object {
        private const val ITEM_COUNT_THRESHOLD = 2
    }

    private var showAllItems: Boolean = false

    private fun toggleItems() {
        showAllItems = !showAllItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = QuestionAnswerItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.bind(item.first, item.second)
        // 마지막 아이템 뒤에만 "펼치기"/"접기" 버튼을 보이게 설정
        if (position == ITEM_COUNT_THRESHOLD - 1 && !showAllItems && items.size > ITEM_COUNT_THRESHOLD) {
            holder.showSeeMore(true)
            holder.setSeeMoreText("펼쳐서 보기")
            holder.seeSeeMoreClickListener { toggleItems() }
        } else if (position == items.size - 1 && showAllItems) {
            holder.showSeeMore(true)
            holder.setSeeMoreText("접기")
            holder.seeSeeMoreClickListener { toggleItems() }
        } else {
            holder.showSeeMore(false)
        }
    }

    override fun getItemCount(): Int {
        return if (showAllItems || items.size <= ITEM_COUNT_THRESHOLD) items.size else ITEM_COUNT_THRESHOLD
    }

    class ViewHolder(private val binding: QuestionAnswerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(question: String, answer: String) {
            binding.question.text = question
            binding.answer.text = answer
        }

        fun showSeeMore(show: Boolean) {
            binding.ivSeeMore.visibility = if (show) View.VISIBLE else View.GONE
            binding.tvSeeMore.visibility = if (show) View.VISIBLE else View.GONE
        }

        fun setSeeMoreText(text: String) {
            binding.tvSeeMore.text = text
        }

        fun seeSeeMoreClickListener(listener: () -> Unit) {
            binding.tvSeeMore.setOnClickListener { listener() }
        }
    }
}