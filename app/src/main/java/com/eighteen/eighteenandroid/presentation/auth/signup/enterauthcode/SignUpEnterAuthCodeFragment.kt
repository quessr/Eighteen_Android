package com.eighteen.eighteenandroid.presentation.auth.signup.enterauthcode

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentSignUpEnterAuthCodeBinding
import com.eighteen.eighteenandroid.presentation.auth.signup.BaseSignUpContentFragment
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpNextButtonModel
import com.eighteen.eighteenandroid.presentation.common.ModelState
import com.eighteen.eighteenandroid.presentation.common.clearFocus
import com.eighteen.eighteenandroid.presentation.common.hideKeyboardAndRemoveCurrentFocus
import com.eighteen.eighteenandroid.presentation.common.showKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * 인증 코드 입력 페이지
 */
@AndroidEntryPoint
class SignUpEnterAuthCodeFragment :
    BaseSignUpContentFragment<FragmentSignUpEnterAuthCodeBinding>(FragmentSignUpEnterAuthCodeBinding::inflate) {
    override val onMovePrevPageAction: () -> Unit = {
        signUpViewModelContentInterface.phoneNumber = ""
        super.onMovePrevPageAction.invoke()
    }
    override val onMoveNextPageAction: () -> Unit = {
        signUpEnterAuthCodeViewModel.requestConfirmMessage(
            phoneNumber = signUpViewModelContentInterface.phoneNumber,
            verificationCode = getCurrentAuthCode()
        )
    }
    override val progress: Int? = null

    override val signUpNextButtonModel: SignUpNextButtonModel = SignUpNextButtonModel(
        isVisible = true,
        isEnabled = false,
        size = SignUpNextButtonModel.Size.NORMAL,
        buttonText = SignUpNextButtonModel.ButtonText.NEXT
    )

    private val signUpEnterAuthCodeViewModel by viewModels<SignUpEnterAuthCodeViewModel>()

    private var isEnabledTextChangeListener = true
    private var isNeedClearFocus = false

    override fun initView() {
        initInputLayout()
        isNeedClearFocus = true
        bind {
            clInputAuthCode.children.forEach {
                it.clearFocus()
            }
            tvBtnResend.setOnClickListener {
                signUpEnterAuthCodeViewModel.requestSendMessage(phoneNumber = signUpViewModelContentInterface.phoneNumber)
            }
        }
        initStateFlow()
        collectRemainTimeFlow()
    }

    override fun onResume() {
        super.onResume()
        if (isNeedClearFocus) {
            binding.clInputAuthCode.children.forEach {
                it.clearFocus()
            }
        }
        isNeedClearFocus = false
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initInputLayout() {
        bind {
            root.setOnClickListener {
                hideKeyboardAndRemoveCurrentFocus()
            }
            val editTexts = clInputAuthCode.children.toList()
            editTexts.forEachIndexed { idx, it ->
                val editText = it as? EditText
                editText?.let { et ->
                    et.setOnEditorActionListener { _, actionId, event ->
                        if ((event?.keyCode == KeyEvent.KEYCODE_ENTER) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                            clearFocus()
                        }
                        false
                    }
                    et.addTextChangedListener(
                        object : TextWatcher {
                            override fun beforeTextChanged(
                                p0: CharSequence?,
                                p1: Int,
                                p2: Int,
                                p3: Int
                            ) {
                            }

                            override fun onTextChanged(
                                p0: CharSequence?,
                                p1: Int,
                                p2: Int,
                                p3: Int
                            ) {
                            }

                            override fun afterTextChanged(p0: Editable?) {
                                if (!isEnabledTextChangeListener) return
                                updateEditTextView()
                            }

                        })
                    et.setOnTouchListener { _, motionEvent ->
                        when (motionEvent.action) {
                            ACTION_DOWN -> {
                                updateEditTextView()
                            }
                        }
                        true
                    }
                }
            }
        }
    }

    private fun updateEditTextView() {
        val editTexts = binding.clInputAuthCode.children.map { it as? EditText }.toList()
        val currentText = getCurrentAuthCode()
        val focusedIndex =
            (currentText.length - 1).coerceAtLeast(0).coerceAtMost(editTexts.lastIndex)
        val focusTarget = editTexts.getOrNull(focusedIndex)
        isEnabledTextChangeListener = false
        editTexts.forEachIndexed { index, editText ->
            editText?.let { et ->
                et.isSelected = currentText.length > index
                et.setText(currentText.getOrNull(index)?.run { toString() } ?: "")
            }
        }
        focusTarget?.let {
            it.setSelection(it.text.length)
            it.requestFocus()
            showKeyboard(it)
        }
        signUpViewModelContentInterface.setNextButtonModel(
            signUpNextButtonModel = signUpNextButtonModel.copy(
                isEnabled = currentText.length >= editTexts.size
            )
        )
        isEnabledTextChangeListener = true
    }

    private fun getCurrentAuthCode() =
        binding.clInputAuthCode.children.joinToString(separator = "") {
            (it as? EditText)?.text?.toString() ?: ""
        }

    private fun initStateFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                signUpEnterAuthCodeViewModel.sendMessageResultStateFlow.collect {
                    when (it) {
                        is ModelState.Loading -> {
                            //TODO 로딩 처리
                            Log.d("SignUpEnterAuthCodeFragment", "send loading")
                        }
                        is ModelState.Success -> {
                            //do nothing
                        }
                        is ModelState.Error -> {
                            //TODO 에러처리
                            Log.d("SignUpEnterAuthCodeFragment", "send error ${it.throwable}")

                        }
                        is ModelState.Empty -> {
                            //do nothing
                        }
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                signUpEnterAuthCodeViewModel.confirmMessageResultStateFlow.collect {
                    when (it) {
                        is ModelState.Loading -> {
                            //TODO 로딩 처리
                            Log.d("SignUpEnterAuthCodeFragment", "confirm loading")
                        }
                        is ModelState.Success -> {
                            //TODO 확인팝업 적용(팝업 디자인 수정 후 작업 예정)
                            findNavController().navigate(R.id.action_fragmentSignUpEnterAuthCode_to_fragmentSignUpTermsOfService)
                        }
                        is ModelState.Error -> {
                            //TODO 에러처리
                            Log.d("SignUpEnterAuthCodeFragment", "confirm error ${it.throwable}")
                        }
                        is ModelState.Empty -> {
                            //do nothing
                        }
                    }
                }
            }
        }
    }

    private fun collectRemainTimeFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            signUpEnterAuthCodeViewModel.remainTimeFlow.collect {
                binding.tvRemainTime.text = it
            }
        }
    }

    override fun onDestroyView() {
        signUpEnterAuthCodeViewModel.clear()
        super.onDestroyView()
    }
}