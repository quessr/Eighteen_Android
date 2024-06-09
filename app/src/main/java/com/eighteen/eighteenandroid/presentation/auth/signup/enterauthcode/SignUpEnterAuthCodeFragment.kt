package com.eighteen.eighteenandroid.presentation.auth.signup.enterauthcode

import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentSignUpEnterAuthCodeBinding
import com.eighteen.eighteenandroid.presentation.auth.signup.BaseSignUpContentFragment
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpNextButtonModel

class SignUpEnterAuthCodeFragment :
    BaseSignUpContentFragment<FragmentSignUpEnterAuthCodeBinding>(FragmentSignUpEnterAuthCodeBinding::inflate) {
    override val onMovePrevPageAction: () -> Unit = {
        findNavController().popBackStack()
    }
    override val onMoveNextPageAction: () -> Unit = {
        findNavController()
    }
    override val progress: Int? = null

    override val nextButtonModel: SignUpNextButtonModel = SignUpNextButtonModel(
        isVisible = true,
        isEnabled = false,
        size = SignUpNextButtonModel.Size.NORMAL,
        textRes = R.string.sign_up_next
    )

    override fun initView() {

    }

}