package com.eighteen.eighteenandroid.presentation.login

import androidx.fragment.app.viewModels
import com.eighteen.eighteenandroid.databinding.FragmentSignUpBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment

/**
 * 회원가입 기능의 진입점
 * 내부의 ChildFragment에 대해 navigation graph 소유
 */
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {
    private val signUpViewModel by viewModels<SignUpViewModel>()

    override fun initView() {
        bind {
            tvBtnNext.setOnClickListener {

            }
            ivBtnBack.setOnClickListener {

            }
        }
    }

}