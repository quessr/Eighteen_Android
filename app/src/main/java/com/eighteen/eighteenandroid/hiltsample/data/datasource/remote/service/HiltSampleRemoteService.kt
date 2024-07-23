package com.eighteen.eighteenandroid.hiltsample.data.datasource.remote.service

import com.eighteen.eighteenandroid.hiltsample.data.datasource.remote.response.HiltSamplePostResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HiltSampleRemoteService {
    @GET("/posts")
    suspend fun getData(@Query("userId") userId: String): Response<List<HiltSamplePostResponse>>
}