package com.eighteen.eighteenandroid.presentation.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpAction
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpNextButtonModel

/**
 * 회원가입 내용 fragment, 네비게이션 호출을 위해 반드시 해당클래스 구현해서 써야 함
 * @property onMoveNextPageAction : 다음 페이지로 이동 버튼 누를 때 실행하는 action
 * @property onMovePrevPageAction : 이전 페이지 혹은 백버튼 누를 때 실행하는 action
 * @property progress : progress bar 진행도 (0~100), null일 경우 gone
 * @property nextButtonModel : 하단 다음버튼 디자인 모델
 */
//TODO back pressed handle 구현
abstract class SignUpContentBaseFragment<VB : ViewBinding>(bindingFactory: (LayoutInflater) -> VB) :
    BaseFragment<VB>(bindingFactory) {

    private val signUpViewModel by viewModels<SignUpViewModel>(ownerProducer = {
        getContainerFragment() ?: this
    }
    )

    abstract val onMovePrevPageAction: () -> Unit
    abstract val onMoveNextPageAction: () -> Unit
    abstract val progress: Int?
    abstract val nextButtonModel: SignUpNextButtonModel

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActionObserver()
        initButtonValue()
    }

    private fun initButtonValue() {
        signUpViewModel.run {
            setProgress(progress = progress)
            setNextButtonModel(nextButtonModel = nextButtonModel)
        }
    }

    private fun initActionObserver() {
        signUpViewModel.actionEventLiveData.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { action ->
                when (action) {
                    SignUpAction.NEXT -> onMoveNextPageAction.invoke()
                    SignUpAction.PREV -> onMovePrevPageAction.invoke()
                }
            }
        }
    }

    private fun getContainerFragment(): Fragment? {
        var parent = parentFragment
        while (parent != null) {
            if (parent is SignUpContentContainer) break
            parent = parent.parentFragment
        }
        return parent
    }
}