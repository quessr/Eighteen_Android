package com.eighteen.eighteenandroid.presentation.auth.signup.enterbirth

import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentSignUpEnterBirthBinding
import com.eighteen.eighteenandroid.presentation.auth.signup.BaseSignUpContentFragment
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpNextButtonModel

class SignUpEnterBirthFragment :
    BaseSignUpContentFragment<FragmentSignUpEnterBirthBinding>(FragmentSignUpEnterBirthBinding::inflate) {

    override val onMovePrevPageAction: () -> Unit = {
        signUpViewModelContentInterface.nickName = ""
        super.onMovePrevPageAction.invoke()
    }
    override val onMoveNextPageAction: () -> Unit = {
        findNavController().navigate(R.id.action_fragmentSignUpEnterBirth_to_fragmentSignUpEnterSchool)
    }
    override val progress: Int = 60
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