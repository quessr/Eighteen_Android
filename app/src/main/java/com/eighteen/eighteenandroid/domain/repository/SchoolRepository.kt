package com.eighteen.eighteenandroid.domain.repository

import com.eighteen.eighteenandroid.domain.model.School

interface SchoolRepository {
    suspend fun getSchools(schoolName: String): Result<List<School>>
}