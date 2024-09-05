package com.eighteen.eighteenandroid.presentation.auth

import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentSignUpCompletedBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment

class SignUpCompletedFragment :
    BaseFragment<FragmentSignUpCompletedBinding>(FragmentSignUpCompletedBinding::inflate) {
    override fun initView() {
        binding.tvBtnNext.setOnClickListener {
            findNavController().popBackStack(R.id.fragmentLogin, inclusive = true)
        }
    }
}