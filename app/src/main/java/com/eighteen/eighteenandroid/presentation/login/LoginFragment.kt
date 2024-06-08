package com.eighteen.eighteenandroid.presentation.login

import androidx.fragment.app.viewModels
import com.eighteen.eighteenandroid.databinding.FragmentLoginBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment

/**
 * Login 기능의 진입점
 * 내부의 ChildFragment에 대해 navigation graph 소유
 */
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val loginViewModel by viewModels<LoginViewModel>()

    override fun initView() {
        bind {
            tvBtnNext.setOnClickListener {

            }
            ivBtnBack.setOnClickListener {

            }
        }
    }

}