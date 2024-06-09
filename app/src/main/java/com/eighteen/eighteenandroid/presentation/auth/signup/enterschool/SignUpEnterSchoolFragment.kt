package com.eighteen.eighteenandroid.presentation.auth.signup.enterschool

import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.databinding.FragmentSignUpEnterSchoolBinding
import com.eighteen.eighteenandroid.presentation.auth.signup.BaseSignUpContentFragment
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpNextButtonModel

class SignUpEnterSchoolFragment :
    BaseSignUpContentFragment<FragmentSignUpEnterSchoolBinding>(FragmentSignUpEnterSchoolBinding::inflate) {
    override val onMovePrevPageAction: () -> Unit = {
        findNavController().popBackStack()
    }
    override val onMoveNextPageAction: () -> Unit = {
        //TODO 구현
    }
    override val progress: Int = 80
    override val nextButtonModel = SignUpNextButtonModel(
        isVisible = true,
        isEnabled = true,
        size = SignUpNextButtonModel.Size.NORMAL,
        buttonText = SignUpNextButtonModel.ButtonText.NEXT
    )

    override fun initView() {

    }

}