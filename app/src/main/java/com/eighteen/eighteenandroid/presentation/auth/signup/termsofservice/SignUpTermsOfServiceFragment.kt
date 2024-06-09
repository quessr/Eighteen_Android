package com.eighteen.eighteenandroid.presentation.auth.signup.termsofservice

import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentSignUpTermsOfServiceBinding
import com.eighteen.eighteenandroid.presentation.auth.signup.BaseSignUpContentFragment
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpNextButtonModel

/**
 * 약관 동의 페이지
 */
class SignUpTermsOfServiceFragment : BaseSignUpContentFragment<FragmentSignUpTermsOfServiceBinding>(
    FragmentSignUpTermsOfServiceBinding::inflate
) {
    override val onMovePrevPageAction: () -> Unit = {
        findNavController().popBackStack()
    }
    override val onMoveNextPageAction: () -> Unit = {
        //TODO 구현
    }
    override val progress: Int? = null
    override val nextButtonModel = SignUpNextButtonModel(
        isVisible = true,
        isEnabled = true,
        size = SignUpNextButtonModel.Size.FULL,
        textRes = R.string.sign_up_next
    )

    override fun initView() {
    }
}