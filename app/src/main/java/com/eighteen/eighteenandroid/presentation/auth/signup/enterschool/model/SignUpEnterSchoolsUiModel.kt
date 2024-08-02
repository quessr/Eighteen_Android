package com.eighteen.eighteenandroid.presentation.auth.signup.enterschool.model

import com.eighteen.eighteenandroid.domain.model.School

data class SignUpEnterSchoolsUiModel(
    val searchKeyword: String = "",
    val schools: List<School> = emptyList()
)
