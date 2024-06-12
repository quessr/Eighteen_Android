package com.eighteen.eighteenandroid.presentation.auth

import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentLoginBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment

//TODO login 분기 구현
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    override fun initView() {
        bind {
            tvBtnSignUp.setOnClickListener {
                findNavController().navigate(R.id.action_fragmentLogin_to_fragmentSignUp)
            }
        }
    }

}