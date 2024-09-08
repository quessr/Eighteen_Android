package com.eighteen.eighteenandroid.presentation.myprofile.edittenofqna

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentEditTenOfQnaBinding
import com.eighteen.eighteenandroid.databinding.ItemEditTenOfQnaInputBinding
import com.eighteen.eighteenandroid.domain.model.Qna
import com.eighteen.eighteenandroid.domain.model.QnaType
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.eighteen.eighteenandroid.presentation.LoginViewModel
import com.eighteen.eighteenandroid.presentation.common.ModelState
import com.eighteen.eighteenandroid.presentation.common.collectInLifecycle
import com.eighteen.eighteenandroid.presentation.common.showDialogFragment
import com.eighteen.eighteenandroid.presentation.dialog.selectqna.SelectQnaDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditTenOfQnaFragment :
    BaseFragment<FragmentEditTenOfQnaBinding>(FragmentEditTenOfQnaBinding::inflate) {

    private val loginViewModel by activityViewModels<LoginViewModel>()

    @Inject
    lateinit var editTenOfQnaAssistedFactory: EditTenOfQnaViewModel.EditTenOfQnaAssistedFactory

    private val editTenOfQnaViewModel by viewModels<EditTenOfQnaViewModel>(factoryProducer = {
        EditTenOfQnaViewModel.Factory(
            assistedFactory = editTenOfQnaAssistedFactory,
            initQnas = loginViewModel.myProfileStateFlow.value.data?.qna ?: emptyList()
        )
    })

    override fun initView() {
        bind {
            ivBtnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        initStateFlow()
        initFragmentResult()
    }

    private fun openSelectQnaDialog(options: List<QnaType>) {
        val selectQnaDialog = SelectQnaDialogFragment.newInstance(
            requestKey = REQUEST_KEY_SELECT_DIALOG,
            title = getString(R.string.my_profile_edit_ten_of_qna_select_title),
            qnas = options
        )
        showDialogFragment(dialogFragment = selectQnaDialog)
    }

    private fun initStateFlow() {
        collectInLifecycle(editTenOfQnaViewModel.editTenOfQnaModelsStateFlow) {
            binding.llQnas.run {
                removeAllViews()
                isVisible = it.isNotEmpty()
                it.forEachIndexed { index, qna ->
                    val qnaView =
                        createQnaView(model = qna, isDividerVisible = index < it.lastIndex)
                    addView(qnaView)
                }
            }
        }
        collectInLifecycle(editTenOfQnaViewModel.requestEditQnaResultStateFlow) {
            when (it) {
                is ModelState.Loading -> {
                    //TODO 로딩처리
                }
                is ModelState.Success -> {
                    it.data?.getContentIfNotHandled()?.let { qnas ->
                        loginViewModel.editQnas(qnas)
                        findNavController().popBackStack()
                    }
                }
                is ModelState.Error -> {
                    //TODO 에러처리
                }
                else -> {
                    //do nothing
                }
            }
        }
    }

    private fun createQnaView(model: Qna, isDividerVisible: Boolean) =
        ItemEditTenOfQnaInputBinding.inflate(layoutInflater).apply {
            //TODO qna 질문 적용
            tvQuestion.text = model.question.name
            tvAnswer.text = model.answer
            vDivider.visibility = if (isDividerVisible) View.VISIBLE else View.INVISIBLE
        }.root

    private fun initFragmentResult() {
        childFragmentManager.setFragmentResultListener(REQUEST_KEY_SELECT_DIALOG,
            viewLifecycleOwner,
            object : SelectQnaDialogFragment.SelectQnaResultListener() {
                override fun onSelectResult(qnaType: QnaType) {
//                    editTenOfQnaViewModel.addQna(qnaType = qnaType)
                }
            })
    }

    companion object {
        private const val REQUEST_KEY_SELECT_DIALOG = "request_key_select_dialog"
    }
}