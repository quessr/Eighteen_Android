package com.eighteen.eighteenandroid.presentation.auth.signup.enterschool

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eighteen.eighteenandroid.domain.usecase.GetSchoolsUseCase
import com.eighteen.eighteenandroid.presentation.auth.signup.enterschool.model.SignUpEnterSchoolsUiModel
import com.eighteen.eighteenandroid.presentation.common.ModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpEnterSchoolViewModel @Inject constructor(private val getSchoolsUseCase: GetSchoolsUseCase) :
    ViewModel() {
    private val _schoolsStateFlow =
        MutableStateFlow<ModelState<SignUpEnterSchoolsUiModel>>(ModelState.Empty())
    val schoolsStateFlow = _schoolsStateFlow.asStateFlow()


    private var getSchoolsJob: Job? = null
    fun requestGetSchools(schoolName: String) {
        if (schoolsStateFlow.value.data?.searchKeyword == schoolName) return
        getSchoolsJob?.cancel()
        getSchoolsJob = viewModelScope.launch {
            _schoolsStateFlow.value = ModelState.Loading()
            getSchoolsUseCase.invoke(schoolName = schoolName).onSuccess {
                val uiModel = SignUpEnterSchoolsUiModel(searchKeyword = schoolName, schools = it)
                _schoolsStateFlow.value = ModelState.Success(data = uiModel)
            }.onFailure {
                _schoolsStateFlow.value = ModelState.Error(throwable = it)
            }
        }
    }
}