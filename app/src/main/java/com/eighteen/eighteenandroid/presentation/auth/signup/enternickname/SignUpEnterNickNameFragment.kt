package com.eighteen.eighteenandroid.presentation.auth.signup.enternickname

import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.databinding.FragmentSignUpEnterNicknameBinding
import com.eighteen.eighteenandroid.presentation.auth.signup.BaseSignUpContentFragment
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpNextButtonModel

class SignUpEnterNickNameFragment :
    BaseSignUpContentFragment<FragmentSignUpEnterNicknameBinding>(FragmentSignUpEnterNicknameBinding::inflate) {
    override val onMovePrevPageAction: () -> Unit = {
        findNavController().popBackStack()
    }
    override val onMoveNextPageAction: () -> Unit = {
        //TODO 구현
    }
    override val progress: Int = 40
    override val nextButtonModel = SignUpNextButtonModel(
        isVisible = true,
        isEnabled = false,
        size = SignUpNextButtonModel.Size.NORMAL,
        buttonText = SignUpNextButtonModel.ButtonText.NEXT
    )

    override fun initView() {

    }

}