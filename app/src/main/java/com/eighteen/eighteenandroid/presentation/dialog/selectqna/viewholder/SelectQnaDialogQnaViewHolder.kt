package com.eighteen.eighteenandroid.presentation.dialog.selectqna.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.eighteen.eighteenandroid.databinding.ItemSelectQnaDialogQnaBinding
import com.eighteen.eighteenandroid.domain.model.QnaType

class SelectQnaDialogQnaViewHolder(
    private val binding: ItemSelectQnaDialogQnaBinding,
    private val onClick: (QnaType) -> Unit
) : ViewHolder(binding.root) {
    fun onBind(qnaType: QnaType, idx: Int) {
        //TODO qna 질문 적용
        val qnaText = "${idx + 1}. ${qnaType.name}"
        binding.tvQna.text = qnaText
        binding.root.setOnClickListener {
            onClick.invoke(qnaType)
        }
    }
}