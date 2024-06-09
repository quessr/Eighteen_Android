package com.eighteen.eighteenandroid.presentation.auth.signup.enterauthcode

import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentSignUpEnterAuthCodeBinding
import com.eighteen.eighteenandroid.presentation.auth.signup.BaseSignUpContentFragment
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpNextButtonModel

/**
 * 인증 코드 입력 페이지
 */
class SignUpEnterAuthCodeFragment :
    BaseSignUpContentFragment<FragmentSignUpEnterAuthCodeBinding>(FragmentSignUpEnterAuthCodeBinding::inflate) {
    override val onMoveNextPageAction: () -> Unit = {
        findNavController().navigate(R.id.action_fragmentSignUpEnterAuthCode_to_fragmentSignUpTermsOfService)
    }
    override val progress: Int? = null

    override val nextButtonModel: SignUpNextButtonModel = SignUpNextButtonModel(
        isVisible = true,
        isEnabled = false,
        size = SignUpNextButtonModel.Size.NORMAL,
        buttonText = SignUpNextButtonModel.ButtonText.NEXT
    )

    override fun initView() {
        //fixme 네비게이션 테스트 코드 삭제
        binding.tvTest.setOnClickListener {
            onMoveNextPageAction.invoke()
        }
    }

}