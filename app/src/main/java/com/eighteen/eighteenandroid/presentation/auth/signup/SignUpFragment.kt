package com.eighteen.eighteenandroid.presentation.auth.signup

import android.view.ViewGroup.LayoutParams
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.eighteen.eighteenandroid.databinding.FragmentSignUpBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpNextButtonModel

/**
 * 회원가입 / 로그인 기능의 진입점
 * 내부의 ChildFragment에 대해 navigation graph 소유
 */
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate),
    SignUpContentContainer {
    private val signUpViewModel by viewModels<SignUpViewModel>()

    override fun initView() {
        bind {
            tvBtnNext.setOnClickListener {
                signUpViewModel.actionToNextPage()
            }
            ivBtnBack.setOnClickListener {
                signUpViewModel.actionToPrevPage()
            }
        }
        initObserver()
    }

    private fun initObserver() {
        bind {
            signUpViewModel.progressLiveData.observe(viewLifecycleOwner) {
                with(lpbProgress) {
                    isVisible = (it != null)
                    if (it != null) {
                        progress = it
                    }
                }

            }

            signUpViewModel.nextButtonLiveData.observe(viewLifecycleOwner) {
                with(tvBtnNext) {
                    isVisible = it.isVisible
                    isEnabled = it.isEnabled
                    text = context?.getText(it.textRes)
                    layoutParams = layoutParams.apply {
                        width =
                            if (it.size == SignUpNextButtonModel.Size.FULL) LayoutParams.MATCH_PARENT else LayoutParams.WRAP_CONTENT
                    }
                }
            }

        }
    }
}