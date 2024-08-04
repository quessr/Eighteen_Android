package com.eighteen.eighteenandroid.domain.usecase

import com.eighteen.eighteenandroid.domain.repository.SchoolRepository
import javax.inject.Inject

class GetSchoolsUseCase @Inject constructor(private val schoolRepository: SchoolRepository) {
    suspend operator fun invoke(schoolName: String) =
        schoolRepository.getSchools(schoolName = schoolName)
}