package com.eighteen.eighteenandroid.presentation.auth.signup.enterphonenumber

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentSignUpEnterPhoneNumberBinding
import com.eighteen.eighteenandroid.presentation.auth.signup.BaseSignUpContentFragment
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpNextButtonModel
import com.eighteen.eighteenandroid.presentation.common.ModelState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * 휴대폰 번호 입력 페이지
 */
@AndroidEntryPoint
class SignUpEnterPhoneNumberFragment :
    BaseSignUpContentFragment<FragmentSignUpEnterPhoneNumberBinding>(
        FragmentSignUpEnterPhoneNumberBinding::inflate
    ) {
    override val onMovePrevPageAction: () -> Unit = {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }
    override val onMoveNextPageAction: () -> Unit = {
        findNavController().navigate(R.id.action_fragmentSignUpEnterPhoneNumber_to_fragmentSignUpEnterAuthCode)
    }
    override val progress: Int? = null
    override val signUpNextButtonModel: SignUpNextButtonModel =
        SignUpNextButtonModel(isVisible = false)

    private val signUpEnterPhoneNumberViewModel by viewModels<SignUpEnterPhoneNumberViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val savedInput = savedInstanceState?.getString(PHONE_NUMBER_SAVE_INSTANCE_KEY, "")
        binding.etInput.setText(savedInput)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initView() = bind {
        initStateFlow()
        root.setOnClickListener {
            hideKeyboard()
        }
        tvBtnGetAuthCode.setOnClickListener {
            signUpEnterPhoneNumberViewModel.requestSendMessage(phoneNumber = etInput.text.toString())
        }
    }

    private fun initStateFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                signUpEnterPhoneNumberViewModel.sendMessageResultStateFlow.collect {
                    when (it) {
                        is ModelState.Loading -> {
                            //TODO 로딩 처리
                        }
                        is ModelState.Success -> {
                            signUpViewModelContentInterface.phoneNumber = it.data ?: ""
                            findNavController().navigate(R.id.action_fragmentSignUpEnterPhoneNumber_to_fragmentSignUpEnterAuthCode)
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(PHONE_NUMBER_SAVE_INSTANCE_KEY, binding.etInput.text.toString())
    }

    companion object {
        private const val PHONE_NUMBER_SAVE_INSTANCE_KEY = "phone_number_save_instance_key"
    }
}