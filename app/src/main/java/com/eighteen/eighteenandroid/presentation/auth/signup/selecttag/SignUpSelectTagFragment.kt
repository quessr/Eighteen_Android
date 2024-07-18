package com.eighteen.eighteenandroid.presentation.auth.signup.selecttag

import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentSignUpSelectTagBinding
import com.eighteen.eighteenandroid.presentation.auth.signup.BaseSignUpContentFragment
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpNextButtonModel

class SignUpSelectTagFragment :
    BaseSignUpContentFragment<FragmentSignUpSelectTagBinding>(FragmentSignUpSelectTagBinding::inflate) {
    override val onMovePrevPageAction: () -> Unit = {
        signUpViewModelContentInterface.school = null
        super.onMovePrevPageAction.invoke()
    }
    override val onMoveNextPageAction: () -> Unit = {
        findNavController().navigate(R.id.action_fragmentSignUpSelectTag_to_fragmentSignUpAddMedias)
    }

    override val progress: Int? = null
    override val signUpNextButtonModel = SignUpNextButtonModel()
    override fun initView() {
        //TODO 임시 이동 코드 제거
        binding.tvTest.setOnClickListener {
            onMoveNextPageAction.invoke()
        }
    }

}