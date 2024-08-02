package com.eighteen.eighteenandroid.presentation.myprofile.edittenofqna

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentEditTenOfQnaBinding
import com.eighteen.eighteenandroid.domain.model.QnaType
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.eighteen.eighteenandroid.presentation.LoginViewModel
import com.eighteen.eighteenandroid.presentation.common.ModelState
import com.eighteen.eighteenandroid.presentation.common.showDialogFragment
import com.eighteen.eighteenandroid.presentation.dialog.selectqna.SelectQnaDialogFragment
import com.eighteen.eighteenandroid.presentation.myprofile.edittenofqna.model.EditTenOfQnaModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
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
            rvQnas.adapter = EditTenOfQnaAdapter(
                getInput = editTenOfQnaViewModel::getInput,
                setInput = editTenOfQnaViewModel::setInput,
                onClickRemove = editTenOfQnaViewModel::removeQna,
                onClickAdd = {
                    val options =
                        QnaType.values()
                            .filter {
                                editTenOfQnaViewModel.inputModels.map { model -> model.qna }
                                    .contains(it).not()
                            }
                    openSelectQnaDialog(options = options)
                },
            )
            rvQnas.addItemDecoration(EditTenOfQnaItemDecoration())
            rvQnas.itemAnimator = null
            ivBtnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            tvBtnSave.setOnClickListener {
                editTenOfQnaViewModel.requestEditQnas()
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
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                editTenOfQnaViewModel.editTenOfQnaModelStateFlow.collect {
                    (binding.rvQnas.adapter as? EditTenOfQnaAdapter)?.submitList(it)
                    binding.tvBtnSave.isEnabled =
                        it.filterIsInstance<EditTenOfQnaModel.Input>().size >= MINIMUM_QNA_COUNT
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                editTenOfQnaViewModel.requestEditQnaResultStateFlow.collect {
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
        }
    }

    private fun initFragmentResult() {
        childFragmentManager.setFragmentResultListener(REQUEST_KEY_SELECT_DIALOG,
            viewLifecycleOwner,
            object : SelectQnaDialogFragment.SelectQnaResultListener() {
                override fun onSelectResult(qnaType: QnaType) {
                    editTenOfQnaViewModel.addQna(qnaType = qnaType)
                }
            })
    }

    companion object {
        private const val REQUEST_KEY_SELECT_DIALOG = "request_key_select_dialog"
        private const val MINIMUM_QNA_COUNT = 3
    }
}