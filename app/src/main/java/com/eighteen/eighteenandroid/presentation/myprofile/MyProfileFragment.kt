package com.eighteen.eighteenandroid.presentation.myprofile

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.eighteen.eighteenandroid.databinding.FragmentMyProfileBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment
import kotlinx.coroutines.launch

class MyProfileFragment :
    BaseFragment<FragmentMyProfileBinding>(FragmentMyProfileBinding::inflate) {
    override fun initView() {
        initStateFlow()
    }

    private fun initStateFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.loginStateFlow.collect {
                }
            }
        }

    }
}