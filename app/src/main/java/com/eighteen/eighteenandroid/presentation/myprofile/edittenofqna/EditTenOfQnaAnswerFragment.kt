package com.eighteen.eighteenandroid.presentation.myprofile.edittenofqna

import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.databinding.FragmentEditTenOfQnaAnswerBinding
import com.eighteen.eighteenandroid.domain.model.QnaType
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.eighteen.eighteenandroid.presentation.common.getParcelableOrNull
import com.eighteen.eighteenandroid.presentation.common.viewModelsByBackStackEntryId

class EditTenOfQnaAnswerFragment :
    BaseFragment<FragmentEditTenOfQnaAnswerBinding>(FragmentEditTenOfQnaAnswerBinding::inflate) {

    private val editTenOfQnaViewModel by viewModelsByBackStackEntryId<EditTenOfQnaViewModel>(
        destinationIdProducer = {
            arguments?.getInt(ARGUMENT_DESTINATION_ID_KEY) ?: -1
        })
    private val qnaType
        get() = arguments?.getParcelableOrNull(
            ARGUMENT_QNA_TYPE_KEY,
            QnaType::class.java
        )

    override fun initView() {
        bind {
            //TODO qnaName 적용
            qnaType?.let { qnaType ->
                tvTitle.text = qnaType.name
                tvBtnSave.setOnClickListener {
                    editTenOfQnaViewModel.addQna(
                        qnaType = qnaType,
                        answer = etAnswer.text.toString()
                    )
                    findNavController().popBackStack()
                }
            }
            ivBtnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            tvBtnSave.isEnabled = etAnswer.text.isNullOrEmpty().not()
            etAnswer.addTextChangedListener {
                tvBtnSave.isEnabled = it.isNullOrEmpty().not()
            }
        }
    }

    companion object {
        const val ARGUMENT_QNA_TYPE_KEY = "argument_qna_type_key"
        const val ARGUMENT_DESTINATION_ID_KEY = "argument_destination_id_key"
    }
}