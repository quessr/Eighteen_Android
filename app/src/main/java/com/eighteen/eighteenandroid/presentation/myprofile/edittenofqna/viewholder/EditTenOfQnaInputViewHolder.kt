package com.eighteen.eighteenandroid.presentation.myprofile.edittenofqna.viewholder

import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.eighteen.eighteenandroid.databinding.ItemEditTenOfQnaInputBinding
import com.eighteen.eighteenandroid.domain.model.QnaType
import com.eighteen.eighteenandroid.presentation.myprofile.edittenofqna.model.EditTenOfQnaModel

class EditTenOfQnaInputViewHolder(
    private val binding: ItemEditTenOfQnaInputBinding,
    private val getInput: (QnaType) -> String,
    private val setInput: (QnaType, String) -> Unit,
    private val onClickRemove: (QnaType) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(model: EditTenOfQnaModel.Input) {
        //TODO 질문 내용 추가 후 텍스트 적용
        with(binding) {
            val questionText = "${model.position}. ${model.qna.name}"
            tvQuestion.text = questionText
            etAnswer.setText(getInput(model.qna))
            etAnswer.addTextChangedListener {
                setInput(model.qna, it?.toString() ?: "")
            }
            ivBtnRemove.setOnClickListener {
                onClickRemove.invoke(model.qna)
            }
        }
    }
}