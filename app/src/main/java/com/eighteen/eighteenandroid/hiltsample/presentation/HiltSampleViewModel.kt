package com.eighteen.eighteenandroid.hiltsample.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eighteen.eighteenandroid.hiltsample.domain.model.HiltSamplePost
import com.eighteen.eighteenandroid.hiltsample.domain.usecase.HiltSampleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HiltSampleViewModel @Inject constructor(
    private val hiltSampleUseCase: HiltSampleUseCase
) : ViewModel() {

    init {
        fetchTestData()
    }
    //Livedata
//    private val _postLiveData = MutableLiveData<List<HiltSamplePost>>(emptyList())
//    val postLiveData: LiveData<List<HiltSamplePost>> = _postLiveData

    //StateFlow
    private val _textStateFlow = MutableStateFlow<List<HiltSamplePost>>(emptyList())
    val textStateFlow: StateFlow<List<HiltSamplePost>> = _textStateFlow

    private var userId = 1
    fun fetchTestData() {
        viewModelScope.launch {
            hiltSampleUseCase.invoke(userId.toString()).onSuccess {
                _textStateFlow.value = it
                userId++
            }.onFailure {
                //에러처리
            }
        }
    }
}