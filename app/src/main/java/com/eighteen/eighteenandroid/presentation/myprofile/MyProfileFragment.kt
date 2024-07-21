package com.eighteen.eighteenandroid.presentation.myprofile

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.eighteen.eighteenandroid.databinding.FragmentMyProfileBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment
import kotlinx.coroutines.launch

//TODO 뷰홀더 어댑터 ItemDecoration 생성
class MyProfileFragment :
    BaseFragment<FragmentMyProfileBinding>(FragmentMyProfileBinding::inflate) {

    private val myProfileViewModel by viewModels<MyProfileViewModel>()

    override fun initView() {
        initStateFlow()
    }

    private fun initStateFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.loginStateFlow.collect {
                    if (it.isSuccess()) {
                        it.data?.profile?.let { profile ->
                            myProfileViewModel.setProfile(profile)
                        }
                    }
                }
            }
        }
    }
}