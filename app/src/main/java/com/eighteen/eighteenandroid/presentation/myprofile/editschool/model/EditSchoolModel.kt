package com.eighteen.eighteenandroid.presentation.myprofile.editschool.model

import com.eighteen.eighteenandroid.domain.model.School

data class EditSchoolModel(
    val searchKeyword: String = "",
    val schools: List<School> = emptyList()
)
