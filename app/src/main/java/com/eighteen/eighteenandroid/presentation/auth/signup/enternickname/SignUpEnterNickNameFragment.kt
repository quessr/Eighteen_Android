package com.eighteen.eighteenandroid.presentation.auth.signup.enternickname

import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentSignUpEnterNicknameBinding
import com.eighteen.eighteenandroid.presentation.auth.signup.BaseSignUpContentFragment
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpNextButtonModel

class SignUpEnterNickNameFragment :
    BaseSignUpContentFragment<FragmentSignUpEnterNicknameBinding>(FragmentSignUpEnterNicknameBinding::inflate) {
    override val onMoveNextPageAction: () -> Unit = {
        findNavController().navigate(R.id.action_fragmentSignUpEnterNickname_to_fragmentSignUpEnterBirth)
    }
    override val progress: Int = 40
    override val signUpNextButtonModel = SignUpNextButtonModel(
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