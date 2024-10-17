package com.eighteen.eighteenandroid.presentation.dialog.selectqna.viewholder

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.ItemSelectQnaDialogQnaBinding
import com.eighteen.eighteenandroid.domain.model.QnaType
import com.eighteen.eighteenandroid.presentation.dialog.selectqna.model.SelectQnaDialogModel

class SelectQnaDialogQnaViewHolder(
    private val binding: ItemSelectQnaDialogQnaBinding,
    private val onClick: (QnaType) -> Unit
) : ViewHolder(binding.root) {
    fun onBind(model: SelectQnaDialogModel, idx: Int) {
        //TODO qna 질문 적용
        binding.tvQna.run {
            val qnaText = "${idx + 1}. ${model.qnaType.name}"
            text = qnaText
            val colorRes = if (model.isEnabled) R.color.black else R.color.main_color
            val styleRes =
                if (model.isEnabled) R.style.pretendard_regular_14 else R.style.pretendard_bold_14
            setTextColor(ContextCompat.getColor(binding.root.context, colorRes))
            setTextAppearance(styleRes)
        }

        binding.root.setOnClickListener {
            if (model.isEnabled) onClick.invoke(model.qnaType)
        }
    }
}