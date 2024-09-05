package com.eighteen.eighteenandroid.presentation.auth.signup.selecttag

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentSignUpSelectTagBinding
import com.eighteen.eighteenandroid.presentation.auth.signup.BaseSignUpContentFragment
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpNextButtonModel
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpPage
import com.eighteen.eighteenandroid.presentation.common.collectInLifecycle

class SignUpSelectTagFragment :
    BaseSignUpContentFragment<FragmentSignUpSelectTagBinding>(FragmentSignUpSelectTagBinding::inflate) {
    override val onMovePrevPageAction: () -> Unit = {
        signUpViewModelContentInterface.setPageClearEvent(page = SignUpPage.ENTER_SCHOOL)
        super.onMovePrevPageAction.invoke()
    }
    override val onMoveNextPageAction: () -> Unit = {
        signUpViewModelContentInterface.tag = signUpSelectTagViewModel.selectedTag?.tag
        findNavController().navigate(R.id.action_fragmentSignUpSelectTag_to_fragmentSignUpAddMedias)
    }

    override val progress: Int = 80
    override val signUpNextButtonModel = SignUpNextButtonModel(isVisible = true, isEnabled = false)

    private val signUpSelectTagViewModel by viewModels<SignUpSelectTagViewModel>()

    override fun initView() {
        bind {
            rvTags.adapter = SignUpSelectTagAdapter(onClick = signUpSelectTagViewModel::selectTag)
            rvTags.itemAnimator = null
            rvTags.addItemDecoration(SignUpSelectTagItemDecoration())
        }
        initStateFlow()
    }

    private fun initStateFlow() {
        collectInLifecycle(signUpSelectTagViewModel.signUpModelStateFlow) {
            (binding.rvTags.adapter as? SignUpSelectTagAdapter)?.submitList(it)
            signUpViewModelContentInterface.setNextButtonModel(
                signUpNextButtonModel = signUpNextButtonModel.copy(
                    isEnabled = it.any { tagModel -> tagModel.isSelected })
            )
        }

        collectInLifecycle(signUpViewModelContentInterface.pageClearEvent) {
            if (it.peekContent() == SignUpPage.SELECT_TAG) {
                it.getContentIfNotHandled()?.run {
                    signUpViewModelContentInterface.tag = null
                    signUpSelectTagViewModel.clear()
                    binding.rvTags.scrollToPosition(0)
                }
            }
        }
    }
}