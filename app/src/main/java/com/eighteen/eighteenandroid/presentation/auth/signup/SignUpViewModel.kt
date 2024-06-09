package com.eighteen.eighteenandroid.presentation.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpAction
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpNextButtonModel
import com.eighteen.eighteenandroid.presentation.common.livedata.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject

//TODO 로그인, 회원가입 관련 api 연동, 입력받은 정보 저장
@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel(), SignUpViewModelContainerInterface {
    private val _actionEventLiveData = MutableLiveData<Event<SignUpAction>>()
    val actionEventLiveData: LiveData<Event<SignUpAction>> = _actionEventLiveData

    private val _nextButtonLiveData = MutableLiveData<SignUpNextButtonModel>()
    val nextButtonLiveData: LiveData<SignUpNextButtonModel> = _nextButtonLiveData

    //진행도 표시 0일경우 비어있음, 100일경우 최대, null일 경우 gone
    private val _progressLiveData = MutableLiveData<Int?>(null)
    val progressLiveData: LiveData<Int?> = _progressLiveData

    override var id: String = ""
    override var nickName: String = ""
    override var birth: Date = Date()
    override var school: String = ""
    override var medias: List<String> = emptyList()

    fun actionToPrevPage() {
        _actionEventLiveData.value = Event(SignUpAction.PREV)
    }

    fun actionToNextPage() {
        _actionEventLiveData.value = Event(SignUpAction.NEXT)
    }

    fun setNextButtonModel(nextButtonModel: SignUpNextButtonModel) {
        _nextButtonLiveData.value = nextButtonModel
    }

    /**
     * @param progress : 진행도 표시 값 (0~100) null일 경우 gone
     */
    fun setProgress(progress: Int?) {
        _progressLiveData.value = progress
    }
}