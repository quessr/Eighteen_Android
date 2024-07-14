package com.eighteen.eighteenandroid.presentation.auth.signup

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentSignUpBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.eighteen.eighteenandroid.presentation.FullWebViewFragment
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpEditMediaAction
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpNextButtonModel
import com.eighteen.eighteenandroid.presentation.common.hideKeyboardAndRemoveCurrentFocus
import com.eighteen.eighteenandroid.presentation.common.livedata.EventObserver
import com.eighteen.eighteenandroid.presentation.common.requestPermissions
import com.eighteen.eighteenandroid.presentation.common.viewModelsByBackStackEntry
import com.eighteen.eighteenandroid.presentation.editmedia.BaseEditMediaFragment.Companion.EDIT_MEDIA_POP_DESTINATION_ID_KEY
import com.eighteen.eighteenandroid.presentation.editmedia.EditMediaViewModel

/**
 * 회원가입 기능의 진입점
 * 내부의 ChildFragment에 대해 navigation graph 소유
 */
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate),
    SignUpContentContainer {
    private val signUpViewModel by viewModels<SignUpViewModel>()

    private val editMediaViewModel by viewModelsByBackStackEntry<EditMediaViewModel>()

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val navHostFragment =
                childFragmentManager.findFragmentById(R.id.fcvSignUpContentContainer) as? NavHostFragment
            val backStackEntryCount =
                navHostFragment?.childFragmentManager?.backStackEntryCount ?: 0
            if (backStackEntryCount <= 0) findNavController().popBackStack()
            else signUpViewModel.actionToPrevPage()
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(onBackPressedCallback)
    }

    override fun onDestroyView() {
        onBackPressedCallback.remove()
        super.onDestroyView()
    }

    override fun initView() {
        bind {
            tvBtnNext.setOnClickListener {
                signUpViewModel.actionToNextPage()
            }
            ivBtnBack.setOnClickListener {
                signUpViewModel.actionToPrevPage()
            }
            root.setOnClickListener {
                hideKeyboardAndRemoveCurrentFocus()
            }
        }
        initSignUpObserver()
        initEditMediaObserver()
        initActionEventObserver()
    }

    private fun initSignUpObserver() = with(signUpViewModel) {
        bind {
            progressLiveData.observe(viewLifecycleOwner) {
                with(lpbProgress) {
                    isVisible = (it != null)
                    if (it != null) {
                        progress = it
                    }
                }

            }

            nextButtonLiveData.observe(viewLifecycleOwner) {
                with(tvBtnNext) {
                    isVisible = it.isVisible
                    isEnabled = it.isEnabled
                    text = context?.getText(it.buttonText.strRes)
                    val textColor = ContextCompat.getColor(
                        context,
                        if (it.isEnabled) R.color.white else R.color.grey_02
                    )
                    setTextColor(textColor)
                    layoutParams = layoutParams.apply {
                        width =
                            if (it.size == SignUpNextButtonModel.Size.FULL) LayoutParams.MATCH_PARENT else LayoutParams.WRAP_CONTENT
                    }
                }
            }

            editMediaActionEventLiveData.observe(
                viewLifecycleOwner,
                EventObserver { action ->
                    val bundle = Bundle().apply {
                        putInt(EDIT_MEDIA_POP_DESTINATION_ID_KEY, R.id.fragmentSignUp)
                    }
                    editMediaViewModel.setMediaUriString(uriString = action.uriString)
                    when (action) {
                        is SignUpEditMediaAction.EditImage -> findNavController().navigate(
                            R.id.action_fragmentSignUp_to_fragmentEditImage,
                            bundle
                        )
                        is SignUpEditMediaAction.EditVideo -> {
                            val onGranted = {
                                findNavController().navigate(
                                    R.id.action_fragmentSignUp_to_fragmentEditVideo,
                                    bundle
                                )
                            }
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                onGranted.invoke()
                            } else {
                                //TODO 권한요청 거부 시 작업(팝업 or 세팅 이동 등) 논의
                                requestPermissions(
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    onGranted = onGranted
                                )
                            }
                        }
                    }
                })
        }
    }

    private fun initActionEventObserver() {
        signUpViewModel.openWebViewLiveData.observe(viewLifecycleOwner, EventObserver { url ->
            val bundle = bundleOf(FullWebViewFragment.URL_ARGUMENT_KEY to url)
            findNavController().navigate(
                R.id.action_fragmentSignUp_to_fragmentFullWebView,
                bundle
            )
        })
    }

    private fun initEditMediaObserver() = with(editMediaViewModel) {
        editResultEventLiveData.observe(viewLifecycleOwner, EventObserver {
            signUpViewModel.addMediaResult(it)
        })
    }
}