package com.eighteen.eighteenandroid.presentation.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eighteen.eighteenandroid.common.enums.Tag
import com.eighteen.eighteenandroid.common.safeLet
import com.eighteen.eighteenandroid.common.toByteArray
import com.eighteen.eighteenandroid.domain.model.AuthToken
import com.eighteen.eighteenandroid.domain.model.MediaFile
import com.eighteen.eighteenandroid.domain.model.MediaType
import com.eighteen.eighteenandroid.domain.model.School
import com.eighteen.eighteenandroid.domain.model.SignUpInfo
import com.eighteen.eighteenandroid.domain.usecase.SignUpUseCase
import com.eighteen.eighteenandroid.domain.usecase.UploadMediaFilesUseCase
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpAction
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpEditMediaAction
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpMedia
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpMedias
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpNextButtonModel
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpPage
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpStatusEvent
import com.eighteen.eighteenandroid.presentation.common.ModelState
import com.eighteen.eighteenandroid.presentation.common.livedata.Event
import com.eighteen.eighteenandroid.presentation.editmedia.model.EditMediaResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val uploadMediaFilesUseCase: UploadMediaFilesUseCase
) : ViewModel(),
    SignUpViewModelContentInterface {
    private val _actionEventLiveData = MutableLiveData<Event<SignUpAction>>()
    val actionEventLiveData: LiveData<Event<SignUpAction>> = _actionEventLiveData

    private val _nextButtonLiveData = MutableLiveData<SignUpNextButtonModel>()
    val nextButtonLiveData: LiveData<SignUpNextButtonModel> = _nextButtonLiveData

    //진행도 표시 0일경우 비어있음, 100일경우 최대, null일 경우 gone
    private val _progressLiveData = MutableLiveData<Int?>(null)
    val progressLiveData: LiveData<Int?> = _progressLiveData

    private val _editMediaActionEventLiveData = MutableLiveData<Event<SignUpEditMediaAction>>()
    val editMediaActionEventLiveData: LiveData<Event<SignUpEditMediaAction>> =
        _editMediaActionEventLiveData

    private val _openWebViewEventLiveData = MutableLiveData<Event<String>>()
    val openWebViewLiveData: LiveData<Event<String>> = _openWebViewEventLiveData

    private val _signUpResultStateFlow =
        MutableStateFlow<ModelState<AuthToken>>(ModelState.Empty())
    override val signUpResultStateFlow: StateFlow<ModelState<AuthToken>> =
        _signUpResultStateFlow.asStateFlow()

    private val _pageClearEventStateFlow =
        MutableStateFlow(Event(SignUpPage.NOTHING))

    override val pageClearEvent: StateFlow<Event<SignUpPage>> =
        _pageClearEventStateFlow.asStateFlow()

    private val _requestLoginEventLiveData = MutableLiveData<Event<AuthToken>>()
    val requestLoginEventLiveData: LiveData<Event<AuthToken>> = _requestLoginEventLiveData

    override var phoneNumber: String = ""
    override var id: String = ""
    override var nickName: String = ""
    override var birth: Calendar? = null
    override var school: School? = null
    override var tag: Tag? = null

    private val _mediasStateFlow = MutableStateFlow(SignUpMedias())
    override val mediasStateFlow: StateFlow<SignUpMedias> = _mediasStateFlow.asStateFlow()

    private var signUpJob: Job? = null

    private val _statusEvent = MutableLiveData<Event<SignUpStatusEvent>>()
    val statusEvent: LiveData<Event<SignUpStatusEvent>> = _statusEvent

    fun actionToPrevPage() {
        _actionEventLiveData.value = Event(SignUpAction.PREV)
    }

    fun actionToNextPage() {
        _actionEventLiveData.value = Event(SignUpAction.NEXT)
    }

    override fun actionOpenWebViewFragment(url: String) {
        _openWebViewEventLiveData.value = Event(url)
    }

    override fun setNextButtonModel(signUpNextButtonModel: SignUpNextButtonModel) {
        _nextButtonLiveData.value = signUpNextButtonModel
    }

    override fun setEditMediaAction(editMediaAction: SignUpEditMediaAction) {
        _editMediaActionEventLiveData.value = Event(editMediaAction)
    }

    /**
     * @param progress : 진행도 표시 값 (0~100) null일 경우 gone
     */
    fun setProgress(progress: Int?) {
        _progressLiveData.value = progress
    }

    fun addMediaResult(mediaResult: EditMediaResult) {
        val signUpMedia = when (mediaResult) {
            is EditMediaResult.Image -> SignUpMedia.Image(imageBitmap = mediaResult.imageBitmap)
            is EditMediaResult.Video -> SignUpMedia.Video(uriString = mediaResult.uriString)
        }
        _mediasStateFlow.update {
            it.copy(medias = it.medias + signUpMedia)
        }
    }

    override fun clearMediaResultStateFlow() {
        _mediasStateFlow.value = SignUpMedias()
    }

    override fun requestSignUp() {
        if (signUpJob?.isCompleted == false) return
        signUpJob = viewModelScope.launch {
            safeLet(birth, school, tag) { birth, school, tag ->
                _signUpResultStateFlow.value = ModelState.Loading()
                val uploadMediaResult = uploadMediaFilesUseCase.invoke(
                    uniqueId = id,
                    mediaFiles = mediasStateFlow.value.medias.map { it.toMediaFiles() }).onFailure {
                    _signUpResultStateFlow.value = ModelState.Error(throwable = it)
                    return@launch
                }
                val birthDayString = birth.run {
                    "${get(Calendar.YEAR)}-${get(Calendar.MONTH)}-${get(Calendar.DATE)}"
                }
                val signUpInfo = SignUpInfo(
                    phoneNumber = phoneNumber,
                    uniqueId = id, nickName = nickName, birthDay = birthDayString, school = school,
                    tag = tag,
                    mediaKeys = uploadMediaResult.getOrNull() ?: emptyList()
                )
                signUpUseCase.invoke(signUpInfo = signUpInfo).onSuccess {
                    _signUpResultStateFlow.value = ModelState.Success(data = it)
                }.onFailure {
                    _signUpResultStateFlow.value = ModelState.Error(throwable = it)
                }
            }
        }
    }

    private fun SignUpMedia.toMediaFiles() = when (this) {
        is SignUpMedia.Image -> {
            MediaFile.ByteArrayMedia(
                byteArray = this.imageBitmap.toByteArray(),
                mediaType = MediaType.IMAGE
            )
        }
        is SignUpMedia.Video -> {
            MediaFile.FileMedia(file = File(this.uriString), mediaType = MediaType.VIDEO)
        }
    }

    override fun setPageClearEvent(page: SignUpPage) {
        _pageClearEventStateFlow.value = Event(page)
    }

    override fun removeMedia(position: Int) {
        _mediasStateFlow.update {
            val updatedMedias = it.medias.toMutableList().apply {
                removeAt(position)
            }
            it.copy(
                mainMedia = it.mainMedia.takeIf { target -> updatedMedias.any { target == it } },
                medias = updatedMedias
            )
        }
    }

    override fun setMainMedia(position: Int) {
        _mediasStateFlow.update {
            it.copy(mainMedia = it.medias.getOrNull(position))
        }
    }

    override fun requestLogin(authToken: AuthToken) {
        viewModelScope.launch {
            _requestLoginEventLiveData.value = Event(authToken)
        }
    }

    override fun sendSignUpStatusEvent(event: SignUpStatusEvent) {
        _statusEvent.value = Event(event)
    }

    override fun onCleared() {
        _mediasStateFlow.value.run {
            (medias).filterIsInstance<SignUpMedia.Image>()
                .forEach { it.imageBitmap.recycle() }
        }
        super.onCleared()
    }
}