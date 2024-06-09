package com.eighteen.eighteenandroid.presentation.auth.signup.enterphonenumber

import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentSignUpEnterPhoneNumberBinding
import com.eighteen.eighteenandroid.presentation.auth.signup.BaseSignUpContentFragment
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpNextButtonModel

/**
 * 휴대폰 번호 입력 페이지
 */
class SignUpEnterPhoneNumberFragment :
    BaseSignUpContentFragment<FragmentSignUpEnterPhoneNumberBinding>(
        FragmentSignUpEnterPhoneNumberBinding::inflate
    ) {
    override val onMovePrevPageAction: () -> Unit = {
        findNavController().popBackStack()
    }
    override val onMoveNextPageAction: () -> Unit = {
        findNavController().navigate(R.id.action_fragmentSignUpEnterPhoneNumber_to_fragmentSignUpEnterAuthCode)
    }
    override val progress: Int? = null
    override val nextButtonModel: SignUpNextButtonModel = SignUpNextButtonModel(isVisible = false)

    override fun initView() {
        //fixme 임시 테스트 코드 삭제
        binding.tvTest.setOnClickListener {
            onMoveNextPageAction.invoke()
        }
    }
}