package com.eighteen.eighteenandroid.presentation.auth.signup

import com.eighteen.eighteenandroid.common.enums.Tag
import com.eighteen.eighteenandroid.domain.model.LoginResultInfo
import com.eighteen.eighteenandroid.domain.model.School
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpEditMediaAction
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpMedia
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpNextButtonModel
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpPage
import com.eighteen.eighteenandroid.presentation.common.ModelState
import com.eighteen.eighteenandroid.presentation.common.livedata.Event
import kotlinx.coroutines.flow.StateFlow
import java.util.Calendar

/**
 * 회원가입 과정 중 받는 정보들을 저장 혹은 child에서 parent에 event를 전달하기 위한 interface
 */
interface SignUpViewModelContentInterface {
    var phoneNumber: String
    var id: String
    var nickName: String
    var birth: Calendar?
    var school: School?
    var tag: Tag?
    val mediasStateFlow: StateFlow<List<SignUpMedia>>
    val signUpResultStateFlow: StateFlow<ModelState<LoginResultInfo>>
    val pageClearEvent: StateFlow<Event<SignUpPage>>
    fun setNextButtonModel(signUpNextButtonModel: SignUpNextButtonModel)
    fun setEditMediaAction(editMediaAction: SignUpEditMediaAction)
    fun clearMediaResultStateFlow()
    fun actionOpenWebViewFragment(url: String)
    fun requestSignUp()
    fun setPageClearEvent(page: SignUpPage)
}