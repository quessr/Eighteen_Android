package com.eighteen.eighteenandroid.presentation.myprofile.editnickname

import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentEditNicknameBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.eighteen.eighteenandroid.presentation.MyViewModel
import com.eighteen.eighteenandroid.presentation.common.ModelState
import com.eighteen.eighteenandroid.presentation.common.collectInLifecycle
import com.eighteen.eighteenandroid.presentation.common.model.nickname.NicknameStatus
import com.eighteen.eighteenandroid.presentation.common.showDialogFragment
import com.eighteen.eighteenandroid.presentation.dialog.ErrorDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditNicknameFragment :
    BaseFragment<FragmentEditNicknameBinding>(FragmentEditNicknameBinding::inflate) {
    private val myViewModel by activityViewModels<MyViewModel>()

    private val editNicknameViewModel by viewModels<EditNicknameViewModel>()
    override fun initView() {
        bind {
            etInput.setText(editNicknameViewModel.nicknameStatusStateFLow.value.inputString)
            etInput.addTextChangedListener {
                editNicknameViewModel.setNickName(nickname = it?.toString() ?: "")
            }
            tvBtnNext.setOnClickListener {
                myViewModel.requestEditNickName(nickName = editNicknameViewModel.nicknameStatusStateFLow.value.inputString)
            }
            ivBtnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        initStateFlow()
    }

    private fun initStateFlow() {
        collectInLifecycle(editNicknameViewModel.nicknameStatusStateFLow) {
            bind {
                when (val status = it.status) {
                    is NicknameStatus.Error -> {
                        tvErrorMessage.isVisible = true
                        tvErrorMessage.text = resources.getString(status.errorStringRes)
                        etInput.setBackgroundResource(R.drawable.bg_rect_stroke_w2_red_r10)
                    }
                    else -> {
                        tvErrorMessage.isVisible = false
                        etInput.setBackgroundResource(R.drawable.bg_text_field)
                    }
                }
                val countText =
                    "${it.inputString.length}/${resources.getInteger(R.integer.sign_up_enter_nickname_max_length)}"
                tvCount.text = countText
                tvBtnNext.isEnabled =
                    it.inputString.isNotEmpty() && it.status !is NicknameStatus.Error
            }
        }
        collectInLifecycle(myViewModel.editProfileEventStateFlow) {
            when (it) {
                is ModelState.Loading -> {
                    binding.inLoading.root.isVisible = true
                }
                is ModelState.Success -> {
                    binding.inLoading.root.isVisible = false
                    it.data?.getContentIfNotHandled()?.let {
                        findNavController().popBackStack()
                    }
                }
                is ModelState.Error -> {
                    binding.inLoading.root.isVisible = false
                    showErrorDialog()

                }
                is ModelState.Empty -> {
                    binding.inLoading.root.isVisible = false
                }
            }
        }
    }

    private fun showErrorDialog() {
        showDialogFragment(ErrorDialogFragment())
    }
}