package com.eighteen.eighteenandroid.presentation.auth.signup.enterid

import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentSignUpEnterIdBinding
import com.eighteen.eighteenandroid.presentation.auth.signup.BaseSignUpContentFragment
import com.eighteen.eighteenandroid.presentation.auth.signup.enterid.model.SignUpEnterIdStatus
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpNextButtonModel
import com.eighteen.eighteenandroid.presentation.common.ModelState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpEnterIdFragment :
    BaseSignUpContentFragment<FragmentSignUpEnterIdBinding>(FragmentSignUpEnterIdBinding::inflate) {
    override val onMoveNextPageAction: () -> Unit = {
        signUpEnterIdViewModel.checkIdValidation()
    }
    override val progress: Int = 20
    override val signUpNextButtonModel = SignUpNextButtonModel(
        isVisible = true,
        isEnabled = false,
        size = SignUpNextButtonModel.Size.NORMAL,
        buttonText = SignUpNextButtonModel.ButtonText.NEXT
    )

    private val signUpEnterIdViewModel by viewModels<SignUpEnterIdViewModel>()

    override fun initView() {
        bind {
            etInput.addTextChangedListener {
                signUpEnterIdViewModel.setInputText(input = it?.toString() ?: "")
            }
            etInput.setText(signUpViewModelContentInterface.id)
        }
        initEnterIdModelStateFlow()
        initCheckValidationStateFlow()
    }

    private fun initEnterIdModelStateFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                signUpEnterIdViewModel.signUpEnterIdModel.collect {
                    bind {
                        when (it.status) {
                            is SignUpEnterIdStatus.Error -> {
                                tvErrorMessage.isVisible = true
                                tvErrorMessage.text = resources.getString(it.status.errorStringRes)
                                etInput.setBackgroundResource(R.drawable.bg_rect_stroke_w2_red_r10)
                            }
                            else -> {
                                tvErrorMessage.isVisible = false
                                etInput.setBackgroundResource(R.drawable.bg_text_field)
                            }
                        }
                        val countText =
                            "${it.inputString.length}/${resources.getInteger(R.integer.sign_up_enter_id_max_length)}"
                        tvCount.text = countText
                        signUpViewModelContentInterface.setNextButtonModel(
                            signUpNextButtonModel = signUpNextButtonModel.copy(isEnabled = it.status is SignUpEnterIdStatus.Success)
                        )
                    }
                }
            }
        }
    }

    private fun initCheckValidationStateFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                signUpEnterIdViewModel.checkIdValidationEventStateFlow.collect {
                    when (it) {
                        is ModelState.Loading -> {
                            //TODO 로딩
                        }
                        is ModelState.Success -> {
                            it.data?.getContentIfNotHandled()?.let {
                                signUpViewModelContentInterface.id =
                                    signUpEnterIdViewModel.signUpEnterIdModel.value.inputString
                                findNavController().navigate(R.id.action_fragmentSignUpEnterId_to_fragmentSignUpEnterNickname)
                            }
                        }
                        is ModelState.Error -> {
                            //TODO 에러처리
                        }
                        else -> {
                            //do nothing
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        signUpViewModelContentInterface.id =
            signUpEnterIdViewModel.signUpEnterIdModel.value.inputString
        super.onDestroyView()
    }
}