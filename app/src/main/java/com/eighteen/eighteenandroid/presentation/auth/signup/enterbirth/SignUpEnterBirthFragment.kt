package com.eighteen.eighteenandroid.presentation.auth.signup.enterbirth

import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.databinding.FragmentSignUpEnterBirthBinding
import com.eighteen.eighteenandroid.presentation.auth.signup.BaseSignUpContentFragment
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpNextButtonModel

class SignUpEnterBirthFragment :
    BaseSignUpContentFragment<FragmentSignUpEnterBirthBinding>(FragmentSignUpEnterBirthBinding::inflate) {
    override val onMovePrevPageAction: () -> Unit = {
        findNavController().popBackStack()
    }
    override val onMoveNextPageAction: () -> Unit = {
        //TODO 구현
    }
    override val progress: Int = 60
    override val nextButtonModel = SignUpNextButtonModel(
        isVisible = true,
        isEnabled = false,
        size = SignUpNextButtonModel.Size.NORMAL,
        buttonText = SignUpNextButtonModel.ButtonText.NEXT
    )

    override fun initView() {

    }

}