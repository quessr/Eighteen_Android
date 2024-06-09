package com.eighteen.eighteenandroid.presentation.auth.signup.enterid

import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentSignUpEnterIdBinding
import com.eighteen.eighteenandroid.presentation.auth.signup.BaseSignUpContentFragment
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpNextButtonModel

class SignUpEnterIdFragment :
    BaseSignUpContentFragment<FragmentSignUpEnterIdBinding>(FragmentSignUpEnterIdBinding::inflate) {
    override val onMoveNextPageAction: () -> Unit = {
        findNavController().navigate(R.id.action_fragmentSignUpEnterId_to_fragmentSignUpEnterNickname)
    }
    override val progress: Int = 20
    override val nextButtonModel = SignUpNextButtonModel(
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