package com.eighteen.eighteenandroid.presentation.auth.signup.enternickname

import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentSignUpEnterNicknameBinding
import com.eighteen.eighteenandroid.presentation.auth.signup.BaseSignUpContentFragment
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpNextButtonModel
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpPage
import com.eighteen.eighteenandroid.presentation.common.collectInLifecycle
import com.eighteen.eighteenandroid.presentation.common.model.nickname.NicknameStatus

class SignUpEnterNickNameFragment :
    BaseSignUpContentFragment<FragmentSignUpEnterNicknameBinding>(FragmentSignUpEnterNicknameBinding::inflate) {
    override val onMoveNextPageAction: () -> Unit = {
        findNavController().navigate(R.id.action_fragmentSignUpEnterNickname_to_fragmentSignUpEnterBirth)
    }

    override val onMovePrevPageAction: () -> Unit = {
        signUpViewModelContentInterface.setPageClearEvent(SignUpPage.ENTER_ID)
        super.onMovePrevPageAction.invoke()
    }
    override val progress: Int = 40
    override val signUpNextButtonModel = SignUpNextButtonModel(
        isVisible = true,
        isEnabled = false,
        size = SignUpNextButtonModel.Size.NORMAL,
        buttonText = SignUpNextButtonModel.ButtonText.NEXT
    )

    private val signUpEnterNickNameViewModel by viewModels<SignUpEnterNickNameViewModel>()

    override fun initView() {
        bind {
            etInput.addTextChangedListener {
                signUpEnterNickNameViewModel.setInputText(input = it?.toString() ?: "")
            }
            etInput.setText(signUpViewModelContentInterface.nickName)
        }
        initStateFlow()
    }

    private fun initStateFlow() {
        collectInLifecycle(signUpEnterNickNameViewModel.nicknameModel) {
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
                signUpViewModelContentInterface.setNextButtonModel(
                    signUpNextButtonModel = signUpNextButtonModel.copy(isEnabled = it.status is NicknameStatus.Success)
                )
            }
        }

        collectInLifecycle(signUpViewModelContentInterface.pageClearEvent) {
            if (it.peekContent() == SignUpPage.ENTER_NICK_NAME) {
                it.getContentIfNotHandled()?.run {
                    signUpViewModelContentInterface.nickName = ""
                    binding.etInput.setText("")
                }
            }
        }
    }

    override fun onDestroyView() {
        signUpViewModelContentInterface.nickName =
            signUpEnterNickNameViewModel.nicknameModel.value.inputString
        super.onDestroyView()
    }
}