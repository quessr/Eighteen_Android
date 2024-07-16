package com.eighteen.eighteenandroid.data.datasource.remote.service

import com.eighteen.eighteenandroid.data.datasource.remote.response.ApiResult
import com.eighteen.eighteenandroid.data.datasource.remote.response.SchoolResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SchoolService {
    @GET("/v1/api/schools")
    suspend fun getSchools(@Query("schoolName") schoolName: String): Response<ApiResult<List<SchoolResponse>>>
}