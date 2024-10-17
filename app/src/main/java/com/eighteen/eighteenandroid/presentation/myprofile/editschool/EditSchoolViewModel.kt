package com.eighteen.eighteenandroid.presentation.myprofile.editschool

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eighteen.eighteenandroid.domain.model.School
import com.eighteen.eighteenandroid.domain.usecase.GetSchoolsUseCase
import com.eighteen.eighteenandroid.presentation.common.ModelState
import com.eighteen.eighteenandroid.presentation.myprofile.editschool.model.EditSchoolModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditSchoolViewModel @Inject constructor(private val getSchoolsUseCase: GetSchoolsUseCase) :
    ViewModel() {
    private val _schoolsStateFlow =
        MutableStateFlow<ModelState<EditSchoolModel>>(ModelState.Empty())
    val schoolsStateFlow = _schoolsStateFlow.asStateFlow()

    var selectedSchool: School? = null

    private var getSchoolsJob: Job? = null
    fun requestGetSchools(schoolName: String) {
        if (schoolsStateFlow.value.data?.searchKeyword == schoolName) return
        getSchoolsJob?.cancel()
        getSchoolsJob = viewModelScope.launch {
            _schoolsStateFlow.value = ModelState.Loading()
            getSchoolsUseCase.invoke(schoolName = schoolName).onSuccess {
                val uiModel = EditSchoolModel(searchKeyword = schoolName, schools = it)
                _schoolsStateFlow.value = ModelState.Success(data = uiModel)
            }.onFailure {
                _schoolsStateFlow.value = ModelState.Error(throwable = it)
            }
        }
    }
}