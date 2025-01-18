package com.eighteen.eighteenandroid.presentation.myprofile.setting.managinginfo

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentManagingInfoBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.eighteen.eighteenandroid.presentation.common.ModelState
import com.eighteen.eighteenandroid.presentation.common.collectInLifecycle
import com.eighteen.eighteenandroid.presentation.common.showDialogFragment
import com.eighteen.eighteenandroid.presentation.dialog.ErrorDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManagingInfoFragment :
    BaseFragment<FragmentManagingInfoBinding>(FragmentManagingInfoBinding::inflate) {
    private val managingInfoViewModel by viewModels<ManagingInfoViewModel>()
    override fun initView() {
        bind {
            ivBtnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            ivBtnDeleteUser.setOnClickListener {
                //TODO show dialog
                managingInfoViewModel.deleteUser()
            }
            ivBtnSignOut.setOnClickListener {
                //TODO show dialog
                managingInfoViewModel.signOut()
            }
        }
        initEvent()
    }

    private fun initEvent() {
        collectInLifecycle(managingInfoViewModel.deleteUserEventChannel) {
            binding.inLoading.root.isVisible = it is ModelState.Loading
            when (it) {
                is ModelState.Success -> {
                    findNavController().popBackStack(R.id.fragmentMain, false)

                }
                is ModelState.Error -> {
                    showDialogFragment(ErrorDialogFragment())
                }
                else -> {
                    //do nothing
                }
            }
        }
        collectInLifecycle(managingInfoViewModel.signOutEventChannel) {
            binding.inLoading.root.isVisible = it is ModelState.Loading
            when (it) {
                is ModelState.Success -> {
                    findNavController().popBackStack(R.id.fragmentMain, false)
                }
                is ModelState.Error -> {
                    showDialogFragment(ErrorDialogFragment())
                }
                else -> {
                    //do nothing
                }
            }
        }
    }

}