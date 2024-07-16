package com.eighteen.eighteenandroid.data.mapper

import com.eighteen.eighteenandroid.data.datasource.remote.response.SchoolResponse
import com.eighteen.eighteenandroid.domain.model.School

object SchoolMapper {
    fun asSchoolUseCaseModel(schoolResponse: SchoolResponse) =
        School(name = schoolResponse.schoolName, address = schoolResponse.address)
}