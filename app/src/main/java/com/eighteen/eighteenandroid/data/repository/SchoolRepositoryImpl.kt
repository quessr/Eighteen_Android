package com.eighteen.eighteenandroid.data.repository

import com.eighteen.eighteenandroid.data.datasource.remote.service.SchoolService
import com.eighteen.eighteenandroid.data.mapper.SchoolMapper
import com.eighteen.eighteenandroid.data.mapper.mapper
import com.eighteen.eighteenandroid.domain.model.School
import com.eighteen.eighteenandroid.domain.repository.SchoolRepository
import javax.inject.Inject

class SchoolRepositoryImpl @Inject constructor(private val schoolService: SchoolService) :
    SchoolRepository {
    override suspend fun getSchools(schoolName: String): Result<List<School>> = runCatching {
        schoolService.getSchools(schoolName = schoolName).mapper {
            it.data?.map { schoolResponse -> SchoolMapper.asSchoolUseCaseModel(schoolResponse = schoolResponse) }
                ?: emptyList()
        }
    }

}