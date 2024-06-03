package com.eighteen.eighteenandroid.presentation.profileDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eighteen.eighteenandroid.databinding.QuestionAnswerItemBinding

class QuestionAnswerAdapter(private val items: List<Pair<String, String>>) : RecyclerView.Adapter<QuestionAnswerAdapter.QuestionAnswerViewHolder>() {
    class QuestionAnswerViewHolder(private val binding: QuestionAnswerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(question: String, answer: String) {
            binding.question.text = question
            binding.answer.text = answer
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionAnswerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = QuestionAnswerItemBinding.inflate(inflater, parent, false)
        return QuestionAnswerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestionAnswerViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item.first, item.second)
    }

    override fun getItemCount(): Int = items.size
}