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
        findNavController().navigate(R.id.action_fragmentSignUpTermsOfService_to_fragmentSignUpEnterId)
    }
    override val progress: Int? = null
    override val signUpNextButtonModel = SignUpNextButtonModel(
        isVisible = true,
        isEnabled = true,
        size = SignUpNextButtonModel.Size.FULL,
        buttonText = SignUpNextButtonModel.ButtonText.NEXT
    )

    override fun initView() {
    }
}