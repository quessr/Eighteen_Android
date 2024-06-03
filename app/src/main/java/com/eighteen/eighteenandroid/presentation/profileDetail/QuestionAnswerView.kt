package com.eighteen.eighteenandroid.presentation.profileDetail

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.eighteen.eighteenandroid.databinding.QuestionAnswerItemBinding

class QuestionAnswerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding: QuestionAnswerItemBinding

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = QuestionAnswerItemBinding.inflate(inflater, this, true)
    }

    fun setQuestion(question: String) {
        binding.question.text = question
    }

    fun setAnswer(answer: String) {
        binding.answer.text = answer
    }
}