package com.eighteen.eighteenandroid.presentation.auth.signup.enterid

import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentSignUpEnterIdBinding
import com.eighteen.eighteenandroid.presentation.auth.signup.BaseSignUpContentFragment
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpNextButtonModel

class SignUpEnterIdFragment :
    BaseSignUpContentFragment<FragmentSignUpEnterIdBinding>(FragmentSignUpEnterIdBinding::inflate) {
    override val onMovePrevPageAction: () -> Unit = {
        findNavController().popBackStack()
    }
    override val onMoveNextPageAction: () -> Unit = {
        //TODO 구현
    }
    override val progress: Int = 20
    override val nextButtonModel = SignUpNextButtonModel(
        isVisible = true,
        isEnabled = false,
        size = SignUpNextButtonModel.Size.NORMAL,
        textRes = R.string.sign_up_next
    )

    override fun initView() {
    }
}