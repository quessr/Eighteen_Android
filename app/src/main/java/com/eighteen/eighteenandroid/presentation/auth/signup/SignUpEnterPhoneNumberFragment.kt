package com.eighteen.eighteenandroid.presentation.auth.signup

import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentSignUpEnterPhoneNumberBinding
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpNextButtonModel

/**
 * 휴대폰 번호 입력 페이지
 */
class SignUpEnterPhoneNumberFragment :
    SignUpContentBaseFragment<FragmentSignUpEnterPhoneNumberBinding>(
        FragmentSignUpEnterPhoneNumberBinding::inflate
    ) {
    override val onMovePrevPageAction: () -> Unit
        get() = {}
    override val onMoveNextPageAction: () -> Unit
        get() = { }
    override val progress: Int?
        get() = null
    override val nextButtonModel: SignUpNextButtonModel
        get() = SignUpNextButtonModel(
            isVisible = true,
            isEnabled = true,
            size = SignUpNextButtonModel.Size.NORMAL,
            textRes = R.string.sign_up_next
        )

    override fun initView() {

    }
}