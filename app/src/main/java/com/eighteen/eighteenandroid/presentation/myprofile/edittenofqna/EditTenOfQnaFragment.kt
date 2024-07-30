package com.eighteen.eighteenandroid.presentation.myprofile.edittenofqna

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.databinding.FragmentEditTenOfQnaBinding
import com.eighteen.eighteenandroid.domain.model.QnaType
import com.eighteen.eighteenandroid.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditTenOfQnaFragment :
    BaseFragment<FragmentEditTenOfQnaBinding>(FragmentEditTenOfQnaBinding::inflate) {
    private val editTenOfQnaViewModel by viewModels<EditTenOfQnaViewModel>()
    override fun initView() {
        bind {
            rvQnas.adapter = EditTenOfQnaAdapter(
                getInput = editTenOfQnaViewModel::getInput,
                setInput = editTenOfQnaViewModel::setInput,
                onClickRemove = editTenOfQnaViewModel::removeQna,
                onClickAdd = {
                    //TODO open dialog
                }
            )
            rvQnas.addItemDecoration(EditTenOfQnaItemDecoration())
            ivBtnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        initStateFlow()
    }

    private fun openSelectQnaDialog(options: List<QnaType>) {

    }

    private fun initStateFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                editTenOfQnaViewModel.editTenOfQnaModelStateFlow.collect {
                    (binding.rvQnas.adapter as? EditTenOfQnaAdapter)?.submitList(it)
                }
            }
        }
    }
}