package com.eighteen.eighteenandroid.presentation.auth.signup.completed

import com.eighteen.eighteenandroid.databinding.FragmentSignUpCompletedBinding
import com.eighteen.eighteenandroid.presentation.auth.signup.BaseSignUpContentFragment
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpNextButtonModel

class SignUpCompletedFragment :
    BaseSignUpContentFragment<FragmentSignUpCompletedBinding>(FragmentSignUpCompletedBinding::inflate) {
    override val onMoveNextPageAction: () -> Unit = {
        //TODO 회원가입 api 호출, 성공 시 회원가입 이전 화면으로 되돌아감
    }
    override val progress: Int? = null
    override val nextButtonModel = SignUpNextButtonModel(
        isVisible = true,
        isEnabled = true,
        size = SignUpNextButtonModel.Size.FULL,
        buttonText = SignUpNextButtonModel.ButtonText.START
    )

    override fun initView() {

    }
}